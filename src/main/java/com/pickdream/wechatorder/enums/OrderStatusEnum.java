package com.pickdream.wechatorder.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 订单状态常量定义
 * */
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
