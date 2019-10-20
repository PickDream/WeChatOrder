package com.pickdream.wechatorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * 商品列表
 * */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Long categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> products;
}
