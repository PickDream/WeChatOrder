package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.beans.UserInfo;
import com.pickdream.wechatorder.respository.UserInfoRepository;
import com.pickdream.wechatorder.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository repository;

    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        return repository.save(userInfo);
    }

    @Override
    public List<UserInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean contains(String openId) {
        return  repository.findById(openId).isPresent();
    }

    @Override
    public void deleteUser(String openId) {
        repository.deleteById(openId);
    }

    @Override
    public List<UserInfo> findByNameLike(String name) {
        return repository.findAllByNickNameContains(name);
    }


}
