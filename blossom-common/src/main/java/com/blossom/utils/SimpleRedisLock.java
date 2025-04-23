package com.blossom.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author Vast
 * @version 1.0
 * @function:redis分布式锁实现，同时确保了锁误删的问题不会发生（采取 Lua脚本确保误删问题解决的原子性）
 */
@SuppressWarnings({"all"})
public class SimpleRedisLock implements ILock{
    private String name;
    private StringRedisTemplate stringRedisTemplate;

    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }



    private static final String KEY_PREFIX="lock:";
    //唯一标识生成：ID_PREFIX通过UUID+线程ID构造全局唯一值，防止不同JVM实例间的锁误删
    private static final String ID_PREFIX= UUID.randomUUID().toString(true)+"-";
    //Lua脚本初始化：
    //静态代码块中创建DefaultRedisScript实例
    //设置Lua脚本路径为类路径下的unlock.lua文件
    //指定脚本返回类型为Long，与Redis执行结果类型匹配
    //原子操作保障：通过Lua脚本实现解锁原子性，避免并发场景下的锁状态判断与删除的非原子操作
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT=new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);

    }
    @Override
    public boolean tryLock(long timeoutSec) {
        //获取线程标识
        String threadId = ID_PREFIX+Thread.currentThread().getId();
        //获取锁
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(KEY_PREFIX+name, threadId, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);//避免自动拆箱造成空指针
    }


    public void unlock() {
        //调用lua脚本
        stringRedisTemplate.execute(UNLOCK_SCRIPT,
                Collections.singletonList(KEY_PREFIX+name),
                ID_PREFIX+Thread.currentThread().getId());
    }

}
