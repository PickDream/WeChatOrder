package com.pickdream.wechatorder.converter;

import com.pickdream.wechatorder.beans.UserInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class WxMpUser2UserInfoConverter {
    public static UserInfo convert(WxMpUser wxMpUser){
        return Optional.of(wxMpUser).map(wxUser -> {
            UserInfo userInfo = null;
            userInfo = UserInfo.builder()
                    .country(wxMpUser.getCountry())
                    .city(wxMpUser.getCity())
                    .language(wxMpUser.getLanguage())
                    .nickName(wxMpUser.getNickname())
                    .openId(wxMpUser.getOpenId())
                    .province(wxMpUser.getProvince())
                    .sex(wxMpUser.getSex())
                    .build();
            return userInfo;
        }).orElse(null);
    }

    public static List<UserInfo> convert(List<WxMpUser> userInfos){
        return userInfos.stream().map(WxMpUser2UserInfoConverter::convert)
                .collect(Collectors.toList());
    }
}
