package com.blossom.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blossom.result.Result;
import com.blossom.entity.VoucherOrder;
import com.blossom.mapper.VoucherOrderMapper;
import com.blossom.service.ISeckillVoucherService;
import com.blossom.service.IVoucherOrderService;
import com.blossom.utils.RedisIdWorker;
import com.blossom.utils.UserHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Vast
 *基于redis消息队列异步处理实现优惠卷秒杀下单功能
 */
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {
    @Resource
    private ISeckillVoucherService seckillVoucherService;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    //优惠卷下单事务代理
    private IVoucherOrderService proxy;

    //初始化Lua脚本
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    static {
        SECKILL_SCRIPT =new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);

    }
    //异步处理线程池
    private static  final ExecutorService SECKILL_ORDER_EXECUTOR= Executors.newSingleThreadExecutor();

    @PostConstruct////在类初始化之后执行，因为当这个类初始化好了之后，随时都是有可能要执行的
    private void init(){
        SECKILL_ORDER_EXECUTOR.submit(new VoucherOrderHandler());
    }

    //基于Redis的Stream结构作为消息队列，实现异步秒杀下单
    private class VoucherOrderHandler implements Runnable{
        String queueName = "stream.orders";
        @Override
        public void run() {
            while (true){
                try {
                    //1.获取消息队列中的订单信息 XREADGROUP GROUP g1 c1 COUNT 1 BLOCK 2000 STREAMS streams.order >
                    List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                            Consumer.from("g1", "c1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)),
                            StreamOffset.create(queueName, ReadOffset.lastConsumed())
                    );
                    //判断消息获取是否成功
                    if(list==null||list.isEmpty()){
                        //如果获取失败，说明没有消息，继续下一次循环
                        continue;
                    }
                    //解析消息中的订单信息
                    MapRecord<String, Object, Object> record = list.get(0);
                    Map<Object, Object> values = record.getValue();
                    VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(values, new VoucherOrder(), true);
                    //如果获取成功，可以下单
                    handleVoucherOrder(voucherOrder);
                    //ACK确认 SACK stream.orders g1 id
                    stringRedisTemplate.opsForStream().acknowledge(queueName,"g1",record.getId());

                    //2.创建订单
                } catch (Exception e) {
                    log.error("处理订单异常",e);
                    handlePendingList();
                }

            }
        }

        private void handlePendingList() {
            while (true){
                try {
                    //1.获取pending-list中的订单信息 XREADGROUP GROUP g1 c1 COUNT 1 BLOCK 2000 STREAMS streams.order 0
                    List<MapRecord<String, Object, Object>> list = stringRedisTemplate.opsForStream().read(
                            Consumer.from("g1", "c1"),
                            StreamReadOptions.empty().count(1),
                            StreamOffset.create(queueName, ReadOffset.from("0"))
                    );
                    //判断消息获取是否成功
                    if(list==null||list.isEmpty()){
                        //如果获取失败，说明pending-list没有消息，结束循环
                        break;
                    }
                    //解析消息中的订单信息
                    MapRecord<String, Object, Object> record = list.get(0);
                    Map<Object, Object> values = record.getValue();
                    VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(values, new VoucherOrder(), true);
                    //如果获取成功，可以下单
                    handleVoucherOrder(voucherOrder);
                    //ACK确认 SACK stream.orders g1 id
                    stringRedisTemplate.opsForStream().acknowledge(queueName,"g1",record.getId());

                    //2.创建订单
                } catch (Exception e) {
                    log.error("处理pending-list订单异常",e);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    //秒杀订单并发控制
    private void handleVoucherOrder(VoucherOrder voucherOrder) {
        //获取用户
        Long userId = voucherOrder.getUserId();
//        创建锁对象
        RLock lock = redissonClient.getLock("lock:order:" + userId);
        //获取锁
        boolean isLock = lock.tryLock(
        );
        //判断是否获取锁成功
        if(!isLock){
            //获取失败，返回错误或重试
            log.error("不允许重复下单");
            return;
        }
        try {
            //绕过多线程事务失效问题（因Spring事务绑定ThreadLocal，此处为异步线程）
            proxy.createVoucherOrder(voucherOrder);
        }finally {
            //释放锁
            lock.unlock();
        }
    }

    @Override
    public Result seckillVoucher(Long voucherId) {
        //获取用户
        Long userId = UserHolder.getUser().getId();
        //6.1 订单id
        long orderId = redisIdWorker.nextId("order");
        //1.执行lua脚本
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(),
                voucherId.toString(), userId.toString(),String.valueOf(orderId)
        );
        //2.判断结果是否为0
        int r = result.intValue();
        if (r!=0){
            //2.1不为0，代表没有购买资格
            return Result.error(r==1?"库存不足":"不能重复下单");
        }
        //获取代理对象
        proxy= (IVoucherOrderService) AopContext.currentProxy();//拿到事务的代理对象
        //3返回订单id
        return Result.success(orderId);
    }

    /**
     * 创建优惠卷订单，实现秒杀优惠卷和一人一单
     * 不能直接使用乐观锁，因为乐观锁只适合更新，不适合增加
     * 这里也不直接使用synchronized，这样添加锁，锁的粒度太粗了
     * 如果锁的粒度太大，会导致每个线程进来都会锁住
     * 同时当前方法被spring的事务控制，如果在方法内部加锁，可能会导致当前方法事务还没有提交，
     * 但是锁已经释放也会导致问题，所以选择将当前方法整体包裹起来，确保事务不会
     * 并且直接在seckillVoucher 方法调用createVoucherOrder也是有问题的
     * 调用的方法，其实是this.的方式调用的，事务想要生效，还得利用代理来生效。
     *
     * 不过这样还是有问题，就是分布式问题：
     * 通过加锁可以解决在单机情况下的一人一单安全问题，但是在集群模式下就不行了；
     * 由于现在部署了多个tomcat，每个tomcat都有一个属于自己的jvm，那么假设在服务器A的tomcat内部，
     * 有两个线程，这两个线程由于使用的是同一份代码，那么他们的锁对象是同一个，是可以实现互斥的，
     * 但是如果现在是服务器B的tomcat内部，又有两个线程，但是他们的锁对象写的虽然和服务器A一样，
     * 但是锁对象却不是同一个，所以线程3和线程4可以实现互斥，但是却无法和线程1和线程2实现互斥，
     * 这就是 集群环境下，syn锁失效的原因，在这种情况下，我们就需要使用分布式锁来解决这个问题。
     * @param voucherOrder
     */
    @Transactional
    public void createVoucherOrder(VoucherOrder voucherOrder) {
        Long userId = voucherOrder.getUserId();
        //查询订单
            int count = query().eq("user_id", userId).eq("voucher_id", voucherOrder.getVoucherId()).count();
            //判断是否存在
            if (count > 0) {
                //用户已经购买过了
                log.error("用户已经购买过一次！");
                return;
            }
            //5.扣减库存
            boolean success = seckillVoucherService.update()
                    .setSql("stock=stock-1")//set stock=stock-1
                    .eq("voucher_id", voucherOrder.getVoucherId()).gt("stock", 0)//where id = ? and stock > ?
                    .update();
            if (!success) {
                //扣减失败
                log.error("库存不足！");
                return;
            }
            save(voucherOrder);

        }

}
