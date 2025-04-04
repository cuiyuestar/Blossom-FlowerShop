package com.blossom.utils;



import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;


@Slf4j
@Component
public class KeyGenerator {


    /**
     * 生成 Redis Key（多个 ID 相加）
     * @param prefix 前缀（如 "activity:"）
     * @param queryParams 包含 ID 字段的对象
     * @return 前缀 + 所有 ID 字段相加后的值（如 "activity:579"）
     */
    public static <T> String generateKey(String prefix, T queryParams) {
        try {
            Class<?> clazz = queryParams.getClass();
            Long real_id = 0L;  // 存储所有id相加的结果

            // 遍历所有字段，累加数值类型的值
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(queryParams);
                if (value instanceof Number) {  // 只处理数值类型（Long/Integer等）
                    real_id += ((Number) value).longValue();
                }
            }
            String key=prefix+real_id;
            return key;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("生成Key失败", e);
        }
    }
}
