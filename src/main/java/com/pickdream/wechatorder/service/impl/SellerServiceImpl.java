package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.beans.SellerInfo;
import com.pickdream.wechatorder.respository.SellerInfoRepository;
import com.pickdream.wechatorder.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sellerService")
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
