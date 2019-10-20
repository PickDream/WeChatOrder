package com.pickdream.wechatorder.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    private String iconPath;
}
