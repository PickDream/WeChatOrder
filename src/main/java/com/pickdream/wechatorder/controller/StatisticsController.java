package com.pickdream.wechatorder.controller;

import com.alipay.api.domain.OrderDetailResult;
import com.pickdream.wechatorder.beans.OrderDetail;
import com.pickdream.wechatorder.respository.OrderDetailRepository;
import com.pickdream.wechatorder.respository.OrderMasterRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.respository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/statistic")
public class StatisticsController {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderMasterRepository orderMasterRepository;

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @GetMapping("all")
    public ModelAndView all(Map<String,Object> map){
        getCount(map);
        return new ModelAndView("/dashbord/all");
    }

    private void getCount(Map<String,Object> map){
        map.put("userCount",userInfoRepository.count());
        map.put("orderCount",orderMasterRepository.count());
        map.put("productCount",productInfoRepository.count());
    }
}
