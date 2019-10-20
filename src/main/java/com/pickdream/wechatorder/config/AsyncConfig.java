package com.pickdream.wechatorder.config;

import com.pickdream.wechatorder.properties.ExecutorProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Autowired
    private ExecutorProp executorProp;

    private int corePoolSize = Runtime.getRuntime().availableProcessors();
    private int maxPoolSize = Runtime.getRuntime().availableProcessors()*5;
    private int queueCapacity = Runtime.getRuntime().availableProcessors()*220;
    private int keepAliveSeconds = 3000;

    @Bean
    public Executor taskExecutor(){
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();

        poolTaskExecutor.setQueueCapacity(queueCapacity);
        poolTaskExecutor.setCorePoolSize(corePoolSize);
        poolTaskExecutor.setMaxPoolSize(maxPoolSize);
        poolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        poolTaskExecutor.setThreadNamePrefix("spring-executor-");
        poolTaskExecutor.initialize();

        return poolTaskExecutor;
    }
}
