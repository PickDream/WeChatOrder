package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.MessageInfo;
import com.pickdream.wechatorder.beans.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageInfoRepository extends JpaRepository<MessageInfo,Long> {
}
