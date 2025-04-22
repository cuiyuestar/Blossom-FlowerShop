package com.blossom;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@EnableCaching
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class})
@MapperScan("com.blossom.mapper")
public class Application {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(Application.class, args);
        log.info("server started");
    }
}
