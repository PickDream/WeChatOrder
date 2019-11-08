package com.pickdream.wechatorder.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品处理过程中异常的常量定义
 * */
@Getter
@AllArgsConstructor
public enum ExceptionEnum {
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_FOUND(12,"订单未找到"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详情未找到"),
    WECHAT_MP_ERROR(14,"微信公众账号方面错误"),
    ORDER_STATUS_ERROR(15,"订单状态错误"),
    ORDER_UPDATE_ERROR(16,"订单更新异常"),
    ORDER_DETAIL_EMPTY(17,"订单商品列表为空"),
    PRODUCT_STATUS_ERROR(18,"商品状态错误")
    ;

    private int code;
    private String msg;
}
