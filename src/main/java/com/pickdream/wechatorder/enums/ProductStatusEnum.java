package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 商品状态常量定义
 * */
@Getter
@AllArgsConstructor
public enum  ProductStatusEnum implements CodeEnum{
    UP(0,"上架中"),
    DOWN(1,"下架")
    ;
    private int code;
    private String msg;
}
