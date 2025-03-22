package com.blossom.annotation;

import com.blossom.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解,用于标识某个方法需要进行公共字段自动填充
 */
@Target(ElementType.METHOD)//表示该注解只能加在方法上
@Retention(RetentionPolicy.RUNTIME)//表示该注解在运行时仍然有效，可以被反射机制读取和处理
public @interface AutoFill {
    //定义一个枚举类型，用于指定数据库操作类型：INSERT、UPDATE（由于公共字段updateTime/createTime/updateUser
    //只有在insert/update时才需要，因此只需指定这两种类型）
    OperationType value();
}
