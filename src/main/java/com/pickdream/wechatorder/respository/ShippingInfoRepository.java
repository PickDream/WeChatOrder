package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.SellerInfo;
import com.pickdream.wechatorder.beans.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, String>{

}
