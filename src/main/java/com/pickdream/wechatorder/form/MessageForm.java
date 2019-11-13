package com.pickdream.wechatorder.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MessageForm {
    @NotNull
    private String openid;
    @NotNull
    private String msg;
    private String url;
}
