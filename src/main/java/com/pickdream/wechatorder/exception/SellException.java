package com.pickdream.wechatorder.exception;

import com.pickdream.wechatorder.enums.ExceptionEnum;

/**
 * 在销售过程中出现的问题
 * */
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ExceptionEnum exceptionEnum){
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
    }
    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
