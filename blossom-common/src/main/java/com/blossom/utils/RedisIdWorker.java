package com.blossom.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Vast
 * @version 1.0
 * @function:redis实现分布式全局唯一id
 */
@SuppressWarnings({"all"})
@Component
public class RedisIdWorker {
    /**
     * 开始时间戳
     */
    private static final long BEGIN_TIMESTAMP=1640995200L;
    /**
     * 序列号位数
     */
    private static final int COUNT_BITS=32;

    private StringRedisTemplate stringRedisTemplate;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix){
        //1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        //将当前LocalDateTime对象转换为UTC时区下的秒级时间戳
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timestamp=nowSecond-BEGIN_TIMESTAMP;
        //2.生成序列号
        //2.1获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        //2.2自增长
        //使用Redis原子操作INCR生成每日自增序列号，键格式为"icr:业务前缀:日期"，保证同业务同天序列唯一
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);
        //3.拼接并返回
        //将时间戳左移32位（腾出低32位空间）
        //使用按位或运算将时间戳与序列号合并为64位长整型
        //最终ID结构：高32位时间戳 | 低32位序列号，符合Snowflake算法变种设计
        return timestamp<<COUNT_BITS | count;
    }

    /**
     * 测试窗口
     * @param args
     */
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
        long second = time.toEpochSecond(ZoneOffset.UTC);
        System.out.println("second = "+second);
    }
}
