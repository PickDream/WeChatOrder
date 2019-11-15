package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    List<UserInfo> findAllByNickNameContains(String name);
}
