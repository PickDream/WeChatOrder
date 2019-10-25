package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品处理过程中的常量
 * */
@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_FOUND(12,"订单未找到"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情未找到"),
    WECHAT_MP_ERROR(14,"微信公众账号方面错误")
    ;

    private int code;
    private String msg;
}
