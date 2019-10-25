package com.pickdream.wechatorder.enums;


import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements CodeEnum {
    NEW(0,"新订单"),
    FINISHED(1,"订单完结"),
    CANCEL(2,"取消订单")
    ;
    private int code;
    private String message;
}
