package com.blossom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blossom.constant.RedisConstants;
import com.blossom.entity.SeckillVoucher;
import com.blossom.entity.Voucher;
import com.blossom.mapper.VoucherMapper;
import com.blossom.result.Result;
import com.blossom.service.ISeckillVoucherService;
import com.blossom.service.IVoucherService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author Vast
 * @version 1.0
 * @function:优惠卷相关功能实现
 */
@SuppressWarnings({"all"})
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements IVoucherService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryVoucherOfShop(Long shopId) {
        // 查询优惠券信息
        List<Voucher> vouchers = getBaseMapper().queryVoucherOfShop(shopId);
        // 返回结果
        return Result.success(vouchers);
    }

    /**
     * 新增秒杀卷功能实现
     * @param voucher
     */
    @Override
    @Transactional
    public void addSeckillVoucher(Voucher voucher) {
        // 保存优惠券
        save(voucher);
        // 保存秒杀信息
        SeckillVoucher seckillVoucher = new SeckillVoucher();
        seckillVoucher.setVoucherId(voucher.getId());
        seckillVoucher.setStock(voucher.getStock());
        seckillVoucher.setBeginTime(voucher.getBeginTime());
        seckillVoucher.setEndTime(voucher.getEndTime());
        seckillVoucherService.save(seckillVoucher);
        //保存秒杀库存到redis中，用户异步优化秒杀业务（lua）
        stringRedisTemplate.opsForValue().set(RedisConstants.SECKILL_STOCK_KEY +voucher.getId(),voucher.getStock().toString());
    }
}
