package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  SexEnum implements CodeEnum {
    MALE(1,"男"),
    FEMALE(0,"女");

    private int code;
    private String msg;
}
