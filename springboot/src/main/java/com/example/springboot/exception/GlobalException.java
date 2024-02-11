package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author HKX
 * date: 2024-01-31 19:44
 * description: 自定义异常
 **/
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody//将返回的Result对象转换成Json
    public Result serviceException(ServiceException e){
        return Result.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody//将返回的Result对象转换成Json
    public Result globalException(Exception e){
        e.printStackTrace();
        return Result.error("500","系统错误");
    }
}
