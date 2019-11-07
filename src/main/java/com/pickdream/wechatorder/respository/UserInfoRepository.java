package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

}
