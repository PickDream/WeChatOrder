package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.form.MessageForm;
import com.pickdream.wechatorder.service.PushService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("pushMessage")
public class PushMassageService implements PushService {
    @Autowired
    private WxMpService wxMpService;

    @Override
    public void discountMessage(String openId) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId("bO5w_v89x6PaodLQGbjPdNgP6XEZEnx4_ZBijvOGNg0")
                .toUser(openId)
                .url("http://maoxin.natapp1.cc/sell/wechat/authorize?returnUrl=http://www.baidu.com")
                .build();
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void payQrcodeMessage(String openId,String qrCodeUrl) {
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId("e8mziieN4RATUPQUWEopeInbMMA7VqJtP6LpXzCNYIc")
                .toUser(openId)
                .url(qrCodeUrl)
                .build();
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMsg(MessageForm message) {
        List<WxMpTemplateData> data = Arrays.asList(new WxMpTemplateData("keyword1",message.getMsg()));
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId("40REPLZvoznKC8QjZEbG0etZdIGqenf7TIxOrizm1fo")
                .toUser(message.getOpenid())
                .url(message.getUrl())
                .data(data)
                .build();
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
    }
}
