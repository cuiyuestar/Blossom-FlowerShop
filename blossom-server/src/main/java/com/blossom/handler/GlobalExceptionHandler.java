package com.blossom.handler;

import com.blossom.constant.MessageConstant;
import com.blossom.exception.BaseException;
import com.blossom.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /* 处理SQLIntegrityConstraintViolationException异常(即唯一索引不唯一异常，比如用户名重复)*/
    @ExceptionHandler
    //Duplicate entry 'zhangsan' for key 'employee.idx_username'(抛出的异常语句)
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //接收异常语句，并赋给变量message
        String message=ex.getMessage();
        if(message.contains("Duplicate entry")){
            //以空格来划分message，划分成一个个单词，并封装到split数组里
            String split[]=message.split(" ");
            //将异常语句message的第三个单词提取出来，这个就是username
            String username=split[2];
            //生成异常信息，返回前端（【用户名】已存在）
            String msg=username+ MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }
        //否则返回未知错误
        else{
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
