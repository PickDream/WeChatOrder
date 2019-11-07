package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Test
    public void findList() {
        Page<OrderDTO>orderDTOS =  orderService.findList(PageRequest.of(0,1));
        log.info("666");
    }
}