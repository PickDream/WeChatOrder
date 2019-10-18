package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.beans.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(Long id);

    List<ProductInfo> findUp();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加减库存
}
