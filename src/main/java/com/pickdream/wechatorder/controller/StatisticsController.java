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

import java.sql.Timestamp;
import java.time.*;
import java.util.*;
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
        getTodayOrder(map);
        getProductSoldCount(map);
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

    private void getTodayOrder(Map<String,Object> map){
        int todayOrderCount=0;
        LocalDate Today= LocalDate.now();
        List<OrderMaster> orderMasters = orderMasterRepository.findAll();
        for(OrderMaster orderOne:orderMasters){
            Date date;
            date=orderOne.getCreateTime();
            if(date.toString().indexOf(Today.toString())==0){
                todayOrderCount++;
            }
        };

        map.put("todayOrderCount",todayOrderCount);
    }


    private void getProductSoldCount(Map<String,Object> map){
        List<OrderMaster> orderMasters = orderMasterRepository.findAll();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        LocalDate Today= LocalDate.now();
        //从订单主表中获取符合标准（一个月内）的订单的id
        List<String> upToStandardOrderIds =new ArrayList<>();
        for(OrderMaster orderMaster:orderMasters){
            Period period = Period.between(
                    orderMaster.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    , Today);
            if(period.getDays()<30){
                upToStandardOrderIds.add(orderMaster.getOrderId());
            }
        }

        //从订单详情表中获取一个月内的订单
        List<OrderDetail> upToStandardOrder= orderDetails.stream()
                .filter((order)->upToStandardOrderIds.contains(order.getOrderId())).collect(Collectors.toList());
        HashMap<String,Integer> nameAndNumMap=new HashMap<>();

        //存 商品名和总数量到 nameAndNumMap 中
        for(OrderDetail order:upToStandardOrder){
            String  productName= order.getProductName();
            int  productQuantity= order.getProductQuantity();
            if(!nameAndNumMap.containsKey(productName)){
                nameAndNumMap.put(productName,productQuantity);
            }else {
                //如果productName不在map里面replace会出null
                nameAndNumMap.replace(productName,nameAndNumMap.get(productName)+productQuantity);
            }
        }


        //销售最好的商品
        String bestProductName="";
        int bestProductNum=0;
        for(String a :nameAndNumMap.keySet()){
            int temp=nameAndNumMap.get(a);
            if(temp>bestProductNum){
                bestProductName=a;
                bestProductNum=temp;
            }
        }

        System.out.println("bestProductName"+bestProductNum);

        List<String> mapKeys=new ArrayList<>();
        for(String name:nameAndNumMap.keySet()){
            mapKeys.add(name);
        }

        map.put("mapKeys",mapKeys);


        map.put("bestProductName",bestProductName);
        map.put("nameAndNumMap",nameAndNumMap);

    }
}
