package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.form.MessageForm;

public interface PushService {
     void discountMessage(String openId);
     void payQrcodeMessage(String openId,String QrCodeUrl);
     void sendMsg(MessageForm message);
}
