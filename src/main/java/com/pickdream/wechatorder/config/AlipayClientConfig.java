package com.pickdream.wechatorder.config;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AlipayClientConfig {

    @Autowired
    AlipayConfig alipayConfig;

    @Bean
    public AlipayClient alipayClient(){
        return new DefaultAlipayClient(alipayConfig.getOpenApiDomain(),alipayConfig.getAppId(),
                alipayConfig.getPrivateKey(),"json","utf-8",alipayConfig.getAlipayPublicKey(),
                alipayConfig.getSignType());
    }

}
