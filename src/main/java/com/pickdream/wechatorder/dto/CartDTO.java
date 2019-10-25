package com.pickdream.wechatorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    /** 商品Id. */
    private Long productId;

    /** 数量. */
    private Integer productQuantity;
}
