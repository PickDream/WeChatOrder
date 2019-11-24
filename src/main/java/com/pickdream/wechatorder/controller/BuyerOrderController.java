package com.pickdream.wechatorder.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.OrderMaster;
import com.pickdream.wechatorder.config.AlipayConfig;
import com.pickdream.wechatorder.converter.OrderForm2OrderDTOConverter;
import com.pickdream.wechatorder.dto.OrderDTO;
import com.pickdream.wechatorder.enums.ExceptionEnum;
import com.pickdream.wechatorder.enums.PayStatusEnum;
import com.pickdream.wechatorder.exception.SellException;
import com.pickdream.wechatorder.form.OrderForm;
import com.pickdream.wechatorder.respository.OrderMasterRepository;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    AlipayClient alipayClient;
    @Autowired
    PushService pushService;

    @Autowired
    AlipayConfig alipayConfig;

    @PostMapping("/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm,
                                                HttpServletRequest request,
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
        map.put("orderId", createResult.getOrderId());
        Cookie[] cookies = request.getCookies();
        String openId = "";
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("openid")){
                openId = cookie.getValue();
                break;
            }
        }
        String qr = QRCodeUtils.generateQRCode(alipayTradePrecreateResponse.getQrCode());
        pushService.payQrcodeMessage(openId,qr);
        return ResultVoUtil.success(map);
    }

    @RequestMapping("/callback")
    public String callback(HttpServletRequest request){
        Map<String,String> params = Maps.newHashMap();
        Map requestParms = request.getParameterMap();
        for (Iterator iterator = requestParms.keySet().iterator(); iterator.hasNext();){
            String name = (String) iterator.next();
            String[] values = (String[]) requestParms.get(name);
            String valueStr = "";
            for (int i=0;i<values.length;i++){
                valueStr = (i==values.length-1)?valueStr+values[i]:valueStr+values[i]+',';
            }
            params.put(name,valueStr);
        }
        //logger.info()
        //根据支付宝的要求删除掉sign_type
        params.remove("sign_type");
        //验证回调的正确性，是不是支付宝发的，
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, alipayConfig.getAlipayPublicKey(),"utf-8",alipayConfig.getSignType());
            if (alipayRSACheckedV2){
                //执行修改订单操作
                Optional<OrderMaster> orderMaster = orderMasterRepository.findById(params.get("out_trade_no"));
                OrderMaster orderMaster1 = orderMaster.get();
                orderMaster1.setPayStatus(PayStatusEnum.SUCCESS.getCode());
                orderMasterRepository.save(orderMaster1);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.error("支付宝验证回调异常");
        }
        //关于支付宝验签,相关信息，https://alipay.open.taobao.com/docs/doc.htm?spm=a219a.7629140.0.0.mFogPC&treeId=193&articleId=103296&docType=1
        //支付宝验签之后，任然需要做检验
        return "success";
    }

    private AlipayTradePrecreateResponse preCreateOrder(OrderDTO orderDTO) throws AlipayApiException {
        AlipayTradePrecreateRequest preCreateRequest = new AlipayTradePrecreateRequest();
        preCreateRequest.setBizContent(getPreCreateInfo(orderDTO));
        preCreateRequest.setNotifyUrl("http://maoxin.natapp1.cc/sell/buyer/order/callback");
        return alipayClient.execute(preCreateRequest);
    }


    private String getPreCreateInfo(OrderDTO orderDTO){
        Gson gson = new Gson();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("out_trade_no",orderDTO.getOrderId());
        requestMap.put("total_amount",orderDTO.getOrderAmount().getAmountMinorLong()/100.0);
        requestMap.put("subject","HUAXIN小菜订单");
        requestMap.put("notify_url","http://maoxin.natapp1.cc/sell/buyer/order/callback");
        return gson.toJson(requestMap);
    }
}
