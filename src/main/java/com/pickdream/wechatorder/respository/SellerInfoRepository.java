package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}