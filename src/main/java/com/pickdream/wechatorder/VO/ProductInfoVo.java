package com.pickdream.wechatorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pickdream.wechatorder.utils.serializer.MoneyToStringSerializer;
import lombok.*;
import org.joda.money.Money;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoVo {

    @JsonProperty("id")
    private Long productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;

    @JsonProperty("price")
    @JsonSerialize(using = MoneyToStringSerializer.class)
    private Money productPrice;


}
