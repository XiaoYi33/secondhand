package com.example.springboot.exception;

import lombok.Getter;

/**
 * author HKX
 * date: 2024-01-31 19:55
 * description: 自定义异常类
 **/
@Getter
public class ServiceException extends RuntimeException {
    private final String code;

    public ServiceException(String msg) {
        super(msg);
        this.code="500";
    }

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;

    }

}
