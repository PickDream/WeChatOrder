package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.enums.ProductStatusEnum;
import com.pickdream.wechatorder.respository.ProductCategoryRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<ProductInfo> findUp() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
