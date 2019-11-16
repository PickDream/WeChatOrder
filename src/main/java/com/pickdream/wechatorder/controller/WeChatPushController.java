package com.pickdream.wechatorder.controller;

import com.pickdream.wechatorder.beans.UserInfo;
import com.pickdream.wechatorder.form.MessageForm;
import com.pickdream.wechatorder.service.PushService;
import com.pickdream.wechatorder.service.UserInfoService;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/push")
public class WeChatPushController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    PushService pushService;

    @GetMapping("/discount")
    public ModelAndView sendDiscount(String openId,Map<String,Object> map){
        pushService.discountMessage(openId);
        return new ModelAndView("common/success",map);
    }
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<UserInfo> userInfos = userInfoService.findAll();
        map.put("users",userInfos);
        return new ModelAndView("/msg/list",map);
    }
    @PostMapping("/msg")
    public ModelAndView list(@Valid MessageForm msg,
                             BindingResult bindingResult,Map<String,Object> map){
        map.put("msg","发送成功");
        map.put("url","/sell/push/list");
        pushService.sendMsg(msg);
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/group")
    public ModelAndView group(Map<String,Object> map){
        List<UserInfo> userInfos = userInfoService.findAll();
        map.put("users",userInfos);
        return new ModelAndView("/msg/group",map);
    }

    @PostMapping("/groupsend")
    public ModelAndView groupSend(String[] openid,String desc,Map<String,Object> map){
        for (String id:openid){
            MessageForm messageForm = new MessageForm();
            messageForm.setMsg(desc);
            messageForm.setOpenid(id);
            messageForm.setUrl("http://maoxin.natapp1.cc/sell/wechat/authorize?returnUrl=http://maoxin.natapp1.cc/#/cc");
            pushService.sendMsg(messageForm);
        }
        map.put("msg","群发消息成功");
        map.put("url","/sell/push/group");
        return new ModelAndView("common/success");
    }

}
