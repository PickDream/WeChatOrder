package com.pickdream.wechatorder.controller;


import com.pickdream.wechatorder.beans.UserInfo;
import com.pickdream.wechatorder.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class SellerUserController {
    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/userInfo")
    public ModelAndView showUser(Map<String,Object> map){
        List<UserInfo> userInfos = userInfoService.findAll();
        map.put("userInfos",userInfos);
        return new ModelAndView("user/user",map);
    }
}
