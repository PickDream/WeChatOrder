package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum  ProductStatusEnum implements CodeEnum{
    UP(0,"上架中"),
    DOWN(1,"下架")
    ;
    private Integer code;
    private String msg;
}
