package com.pickdream.wechatorder.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundApplyRequest;
import com.alipay.api.response.AlipayTradeRefundApplyResponse;
import com.google.gson.Gson;
import com.pickdream.wechatorder.beans.OrderDetail;
import com.pickdream.wechatorder.beans.OrderMaster;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.converter.OrderMaster2OrderDTOConverter;
import com.pickdream.wechatorder.dto.CartDTO;
import com.pickdream.wechatorder.dto.OrderDTO;
import com.pickdream.wechatorder.enums.OrderStatusEnum;
import com.pickdream.wechatorder.enums.PayStatusEnum;
import com.pickdream.wechatorder.enums.ExceptionEnum;
import com.pickdream.wechatorder.exception.SellException;
import com.pickdream.wechatorder.respository.OrderDetailRepository;
import com.pickdream.wechatorder.respository.OrderMasterRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.service.OrderService;
import com.pickdream.wechatorder.service.ProductService;
import com.pickdream.wechatorder.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("orderService")
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    ProductService productService;
    @Autowired
    AlipayClient alipayClient;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        Money orderAmount = Money.of(CurrencyUnit.of("CNY"),0);
        List<OrderDetail> orderDetails =  orderDTO.getOrderDetailList();

        List<CartDTO> cartDTOS = new ArrayList<>();
        //遍历订单详情
        for (OrderDetail orderDetail:orderDetails){
            ProductInfo productInfo = productInfoRepository
                    .findById(orderDetail.getProductId()).orElseThrow(()->new SellException(ExceptionEnum.PRODUCT_NOT_EXIST));
            //计算订单总价
            orderAmount = orderAmount.plus(productInfo.getProductPrice()
                    .multipliedBy(orderDetail.getProductQuantity()));
            //封装订单详细信息
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
            //创建Cart DTO对象
            CartDTO cartDTO = CartDTO.builder()
                    .productId(orderDetail.getProductId())
                    .productQuantity(orderDetail.getProductQuantity())
                    .build();
            cartDTOS.add(cartDTO);
        }
        //写入订单数据库
        orderDTO.setOrderAmount(orderAmount);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster = orderMasterRepository.save(orderMaster);
        orderDTO.setOrderId(orderMaster.getOrderId());
        //扣库存
        productService.decreaseStock(cartDTOS);
        //发送WebSocket消息
        //TODO 发送websocket消息
        return orderDTO;
    }
    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> orderMasterOptional = orderMasterRepository.findById(orderId);

        OrderMaster orderMaster = orderMasterOptional
                .orElseThrow(()->new SellException(ExceptionEnum.ORDER_NOT_FOUND));

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        if (Objects.isNull(orderDetails)){
            throw new SellException(ExceptionEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage =
                orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList =
                OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    /**
     * 取消订单步骤
     * 1. 判断订单是否能被取消
     * 2. 修改订单状态
     * 3. 返还库存
     * 4. 如果已经支付，进行退款操作
     * */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单]订单状态不正确，orderId={},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updatedResult = orderMasterRepository.save(orderMaster);
        if (Objects.isNull(updatedResult)){
            log.error("[取消订单]更新失败,orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_UPDATE_ERROR);
        }
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单]订单详情为空,orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已经支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO 支付退款操作
            String ret = orderRefund(orderDTO.getOrderId());
            if (!"success".equals(ret)){
                log.error("支付宝退款失败");
            }
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单] 订单状态不正确 orderId={},orderStatus={}"
                    ,orderDTO.getOrderId(),orderDTO.getOrderStatus());
            orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
            OrderMaster orderMaster = new OrderMaster();
            BeanUtils.copyProperties(orderDTO,orderMaster);
            OrderMaster updatedResult = orderMasterRepository.save(orderMaster);
            if (updatedResult==null){
                log.error("[取消订单]更新失败,orderMaster={}",orderMaster);
                throw new SellException(ExceptionEnum.ORDER_UPDATE_ERROR);
            }
            return orderDTO;
        }
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
    private String orderRefund(String orderId){
        AlipayTradeRefundApplyRequest alipayTradeRefundApplyRequest  = new AlipayTradeRefundApplyRequest();
        String refundBiz = setRefundInfo(orderId);
        alipayTradeRefundApplyRequest.setBizContent(refundBiz);
        try {
            AlipayTradeRefundApplyResponse response = alipayClient.execute(alipayTradeRefundApplyRequest);
            if (response.isSuccess()){
                return "success";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    private String setRefundInfo(String orderId){
        Gson gson = new Gson();
        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("out_trade_no",orderId);
        OrderDTO orderDTO = findOne(orderId);
        requestMap.put("refund_amount",orderDTO.getOrderAmount().getAmountMinorLong()/100.0);
        requestMap.put("out_request_no","UNIQUE001");
        return gson.toJson(requestMap);
    }
}
