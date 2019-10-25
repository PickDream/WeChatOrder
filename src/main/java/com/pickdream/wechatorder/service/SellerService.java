package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.beans.SellerInfo;

public interface SellerService {
    
    SellerInfo findSellerInfoByOpenid(String openid);
}
