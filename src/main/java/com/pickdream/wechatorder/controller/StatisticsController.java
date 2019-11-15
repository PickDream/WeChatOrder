package com.pickdream.wechatorder.controller;

import com.alipay.api.domain.OrderDetailResult;
import com.pickdream.wechatorder.beans.OrderDetail;
import com.pickdream.wechatorder.beans.OrderMaster;
import com.pickdream.wechatorder.beans.UserInfo;
import com.pickdream.wechatorder.respository.OrderDetailRepository;
import com.pickdream.wechatorder.respository.OrderMasterRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.respository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        statisticsSex(map);
        statisticsOrder(map);
        return new ModelAndView("/dashbord/all");
    }

    private void getCount(Map<String,Object> map){
        map.put("userCount",userInfoRepository.count());
        map.put("orderCount",orderMasterRepository.count());
        map.put("productCount",productInfoRepository.count());
    }

    private void statisticsSex(Map<String,Object> map){
        List<UserInfo> userInfos = userInfoRepository.findAll();
        List<UserInfo> noneList = userInfos
                .stream().filter((userInfo)-> Objects.isNull(userInfo.getSex())).collect(Collectors.toList());
        List<UserInfo> manList = userInfos
                .stream().filter((userInfo)-> (Objects.nonNull(userInfo))&&userInfo.getSex()==1).collect(Collectors.toList());
        List<UserInfo> womanList = userInfos
                .stream().filter((userInfo)-> (Objects.nonNull(userInfo))&&userInfo.getSex()==0).collect(Collectors.toList());
        map.put("man",manList.size());
        map.put("woman",womanList.size());
        map.put("none",noneList.size());
    }

    private void statisticsOrder(Map<String,Object> map){
        List<OrderMaster> orderMasters = orderMasterRepository.findAll();
        List<OrderMaster> newOrder = orderMasters.stream()
                .filter((master)->master.getOrderStatus()==0).collect(Collectors.toList());
        List<OrderMaster> finishOrder = orderMasters.stream()
                .filter((master)->master.getOrderStatus()==1).collect(Collectors.toList());
        List<OrderMaster> cancelOrder = orderMasters.stream()
                .filter((master)->master.getOrderStatus()==2).collect(Collectors.toList());

        map.put("newOrder",newOrder.size());
        map.put("finishOrder",finishOrder.size());
        map.put("cancelOrder",cancelOrder.size());

    }
}
