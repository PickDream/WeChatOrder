package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    //获取所有在架商品
    ResultVo listUpProducts();

    ProductInfo findOne(String productId);
    //增加库存
    void increaseStock(List<CartDTO> cartDTOList);
    void decreaseStock(List<CartDTO> cartDTOList);

}
