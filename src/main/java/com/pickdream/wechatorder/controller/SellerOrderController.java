package com.pickdream.wechatorder.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundApplyRequest;
import com.alipay.api.response.AlipayTradeRefundApplyResponse;
import com.google.gson.Gson;
import com.pickdream.wechatorder.beans.OrderDetail;
import com.pickdream.wechatorder.dto.OrderDTO;
import com.pickdream.wechatorder.enums.ExceptionEnum;
import com.pickdream.wechatorder.enums.OrderStatusEnum;
import com.pickdream.wechatorder.enums.ResultEnum;
import com.pickdream.wechatorder.exception.SellException;
import com.pickdream.wechatorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayClient alipayClient;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "8") Integer size,
                             Map<String,Object> map){

        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        return new ModelAndView("/order/list",map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(String orderId,Map<String,Object> map){
        map.put("url","/sell/seller/order/list");
        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e){
            log.error("[卖家端取消订单]查询不到订单 {0}",e);

            map.put("msg", e.getMessage());
            return new ModelAndView("common/error",map);
        }
        map.put("msg","订单取消成功");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(String orderId, Map<String, Object> map) {
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e) {
            log.error("【卖家端查询订单详情】发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }

        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }
}
