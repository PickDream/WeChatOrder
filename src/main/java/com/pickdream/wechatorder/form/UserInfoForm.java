package com.pickdream.wechatorder.form;

import com.pickdream.wechatorder.beans.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoForm {
    List<UserInfo> infos = new ArrayList<>();

    public List<UserInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<UserInfo> infos) {
        this.infos = infos;
    }
}
