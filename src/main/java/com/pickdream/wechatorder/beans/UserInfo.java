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
public class UserInfo {
    @Id
    String openId;

    String nickName;

    Integer sex;

    String language;

    String city;

    String province;

    String country;

    String imgUrl;
}
