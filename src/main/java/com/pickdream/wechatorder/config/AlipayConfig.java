package com.pickdream.wechatorder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    private String openApiDomain;
    private String pid;
    private String appId;
    private String privateKey;
    private String publicKey;
    private String alipayPublicKey;
    private String signType;


    //当面付配置
    private Integer maxQueryRetry;
    private Long queryDuration;
    private Long maxCancelRetry;
    private Long cancelDuration;

    private Long heartBeatDelay;
    private Long heartbeatDuration;
    private String callback;
}
