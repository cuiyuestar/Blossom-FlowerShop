package com.blossom.aspect;

import com.blossom.annotation.AutoFill;
import com.blossom.context.BaseContext;
import com.blossom.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面类，实现公共字段自动填充的具体逻辑
 */
@Aspect
@Component //交给Bean处理
@Slf4j
public class AutoFillAspect {
    /**
     * 切入点：对哪些类的哪些方法进行拦截
     */

    //拦截mapper下的所有方法，尝试进行切入
    //@annotation(com.sky.annotation.AutoFill) 表示拦截带有AutoFill注解的方法
    //整体意思是：拦截mapper层下所有带AutoFill注解的方法
    @Pointcut("execution(* com.blossom.mapper.*.*(..)) && @annotation(com.blossom.annotation.AutoFill)")
    public void autoFillPointCut(){}

    /**
     * 前置通知：在目标方法执行前执行
     */

    /*autoFIllPointCut表示切入点，即要切入拦截的代码块（这里拦截的是方法）*/
    /*那么@Before("autoFillPointCut()")就表示要在待拦截的方法前进行公共字段赋值，即修改对象的UpdateTime等属性*/
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        //log.info("开始进行公共字段自动填充");
        //获取当前被拦截方法上的数据库操作类型：INSERT、UPDATE
        MethodSignature signature= (MethodSignature) joinPoint.getSignature(); //获取当前被拦截方法的签名
        AutoFill autoFill=signature.getMethod().getAnnotation(AutoFill.class);//获取当前被拦截方法上的AutoFill注解对象
        OperationType operationType=autoFill.value();//从注解对象中获得该方法的操作类型
        //获取被拦截方法上的参数--实体对象
        Object[] args=joinPoint.getArgs();
        if(args==null||args.length==0) return;
        Object entity=args[0];//约定：实体对象放在第一个参数位置，因此args[0]就是实体对象，将它赋给entity

        //准备给公共字段的数据
        LocalDateTime now=LocalDateTime.now();
        Long currentId= BaseContext.getCurrentId();

        //根据不同的操作类型，给实体对象里的公共字段属性通过反射来赋值（比如employee对象里的updateTime、createUser）
        if(operationType== OperationType.INSERT){
            try {
                //获取实体类中的set方法
                Method setCreateTime=entity.getClass().getDeclaredMethod("setCreateTime",LocalDateTime.class);
                Method setCreateUser=entity.getClass().getDeclaredMethod("setCreateUser",Long.class);
                Method setUpdateTime=entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
                Method setUpdateUser=entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);
                //通过set方法给实体类中的属性赋值
                setCreateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                Method setUpdateTime=entity.getClass().getDeclaredMethod("setUpdateTime",LocalDateTime.class);
                Method setUpdateUser=entity.getClass().getDeclaredMethod("setUpdateUser",Long.class);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
