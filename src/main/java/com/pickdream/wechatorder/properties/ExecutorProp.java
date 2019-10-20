package com.pickdream.wechatorder.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "executor",ignoreInvalidFields = true)
public class ExecutorProp {

    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private int keepAliveSecond;
}
