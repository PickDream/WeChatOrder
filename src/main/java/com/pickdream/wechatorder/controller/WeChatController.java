package com.pickdream.wechatorder.controller;

import com.pickdream.wechatorder.beans.UserInfo;
import com.pickdream.wechatorder.config.ProjectUrlConfig;
import com.pickdream.wechatorder.converter.WxMpUser2UserInfoConverter;
import com.pickdream.wechatorder.enums.ExceptionEnum;
import com.pickdream.wechatorder.exception.SellException;
import com.pickdream.wechatorder.respository.UserInfoRepository;
import com.pickdream.wechatorder.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 微信授权过程
 * */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){
        String url = projectUrlConfig.getWechatMpAuthorize()+"/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code, @RequestParam("state")String returnUrl, HttpServletResponse response){

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权]{0}",e);
            throw new SellException(ExceptionEnum.WECHAT_MP_ERROR.getCode(),
                    e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        //1. openId 在UserInfo 是否存在
        //2. 不存在-> 则调用API进行获取用户信息存储DB中
        if (!userInfoService.contains(openId)){
            try {
                WxMpUser wxUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,null);
               UserInfo userInfo = WxMpUser2UserInfoConverter.convert(wxUser);
                userInfoService.saveUserInfo(userInfo);
            } catch (WxErrorException e) {
                log.error("[获取微信用户信息失败]");
                e.printStackTrace();
            }
        }
        Cookie cookie = new Cookie("openid",openId);
        cookie.setMaxAge(5 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:"+ returnUrl + "?openid="+openId;
    }
}
