package com.blossom.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vast
 * @version 1.0
 * @function:配置Redisson客户端
 */
@SuppressWarnings({"all"})
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        //配置
        Config config = new Config();
        config.useSingleServer().setAddress("redis://101.201.65.241:6379").setPassword("v123456");
        //创建RedissonClient对象
        return Redisson.create(config);
    }
}
