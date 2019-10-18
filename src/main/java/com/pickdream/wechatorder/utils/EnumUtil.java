package com.pickdream.wechatorder.utils;

import com.pickdream.wechatorder.enums.CodeEnum;

/**
 *
 * */
public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for (T e:enumClass.getEnumConstants()){
            if (code.equals(e.getCode())){
                return e;
            }
        }
        return null;
    }
}
