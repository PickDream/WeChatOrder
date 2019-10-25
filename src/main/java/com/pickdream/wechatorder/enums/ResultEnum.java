package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum implements CodeEnum {
    SUCCESS(0,"成功"),
    FAILED(1,"失败"),
    PRODUCT_NOT_EXIST(10,"商品不存在")
    ;

    private int code;
    private String msg;
}
