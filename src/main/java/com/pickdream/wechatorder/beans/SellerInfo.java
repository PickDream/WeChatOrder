package com.pickdream.wechatorder.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class SellerInfo {
    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}
