package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.beans.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo saveUserInfo(UserInfo userInfo);

    List<UserInfo> findAll();

    boolean contains(String openId);

    void deleteUser(String openId);

    List<UserInfo> findByNameLike(String name);

}
