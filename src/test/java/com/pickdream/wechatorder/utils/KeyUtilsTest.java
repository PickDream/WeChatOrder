package com.pickdream.wechatorder.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KeyUtilsTest {
    @Autowired
    Executor commonExecutor;
    @Test
    public void getUniqueKey() {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0;i<5;i++){
            commonExecutor.execute(()->{
                for (int j = 0;j<5;j++)
                    KeyUtil.getUniqueKey();
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
            log.info("keyid:{}",KeyUtil.getUniqueKey());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}