package com.pickdream.wechatorder.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pickdream.wechatorder.beans.OrderDetail;
import com.pickdream.wechatorder.enums.OrderStatusEnum;
import com.pickdream.wechatorder.enums.PayStatusEnum;
import com.pickdream.wechatorder.utils.EnumUtil;
import com.pickdream.wechatorder.utils.serializer.Date2LongSerializer;
import lombok.Data;
import org.joda.money.Money;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信 Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private Money orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus ;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
