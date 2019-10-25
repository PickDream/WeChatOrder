package com.pickdream.wechatorder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("application.yml")
@ConfigurationProperties(prefix = "wechat")
public class WeChatMPAccountConfig {

}
