package com.pickdream.wechatorder.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.Gson;
import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.converter.OrderForm2OrderDTOConverter;
import com.pickdream.wechatorder.dto.OrderDTO;
import com.pickdream.wechatorder.enums.ExceptionEnum;
import com.pickdream.wechatorder.exception.SellException;
import com.pickdream.wechatorder.form.OrderForm;
import com.pickdream.wechatorder.service.OrderService;
import com.pickdream.wechatorder.service.PushService;
import com.pickdream.wechatorder.utils.QRCodeUtils;
import com.pickdream.wechatorder.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    PushService pushService;

    @PostMapping("/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ExceptionEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);

        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ExceptionEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        AlipayTradePrecreateResponse alipayTradePrecreateResponse = null;
        try {
            alipayTradePrecreateResponse = preCreateOrder(orderDTO);
        } catch (AlipayApiException e) {
            log.error("创建于支付订单时出错[code:{},msg:{}]",e.getErrCode(),e.getErrMsg());
        }
        //TODO 这里暂时写死
        map.put("orderId", createResult.getOrderId());
        String qr = QRCodeUtils.generateQRCode(alipayTradePrecreateResponse.getQrCode());
        pushService.payQrcodeMessage("oot2Tt-wyXePSQvdg70sGo01URaI",qr);
        return ResultVoUtil.success(map);
    }

//    @RequestMapping("/callback")
//    public String callback(){
//
//    }

    private AlipayTradePrecreateResponse preCreateOrder(OrderDTO orderDTO) throws AlipayApiException {
        AlipayTradePrecreateRequest preCreateRequest = new AlipayTradePrecreateRequest();
        preCreateRequest.setBizContent(getPreCreateInfo(orderDTO));
        return alipayClient.execute(preCreateRequest);
    }


    private String getPreCreateInfo(OrderDTO orderDTO){
        Gson gson = new Gson();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("out_trade_no",orderDTO.getOrderId());
        requestMap.put("total_amount",orderDTO.getOrderAmount().getAmountMinorLong()/100.0);
        requestMap.put("subject","HUAXIN小菜订单");
        return gson.toJson(requestMap);
    }
}
