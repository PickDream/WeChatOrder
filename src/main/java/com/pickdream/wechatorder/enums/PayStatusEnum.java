package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态常量定义
 * */
@Getter
@AllArgsConstructor
public enum PayStatusEnum implements CodeEnum {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
    ;
    private int code;
    private String message;
}
