package com.pickdream.wechatorder.form;

import lombok.Data;
import org.joda.money.Money;


@Data
public class ProductInfoForm {

    private Long productId;

    /** 名字. */
    private String productName;

    /** 单价. */

    private String productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 类目编号. */
    private Long categoryType;
}
