package com.pickdream.wechatorder.service.impl;

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
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    ProductService productService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        Money orderAmount = Money.of(CurrencyUnit.of("CNY"),0);
        List<OrderDetail> orderDetails =  orderDTO.getOrderDetailList();
        //
        List<CartDTO> cartDTOS = new ArrayList<>();
        //遍历订单详情
        orderDetails.stream().forEach((orderDetail)->{
            ProductInfo productInfo = productInfoRepository
                    .getOne(orderDetail.getProductId());
            if (Objects.isNull(productInfo)){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount.plus(productInfo.getProductPrice()
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
        });
        //写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //扣库存
        productService.decreaseStock(cartDTOS);
        return orderDTO;
    }
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        if (Objects.isNull(orderMaster)){
            throw new SellException(ExceptionEnum.ORDER_NOT_FOUND);
        }
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

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        return null;
    }
}
