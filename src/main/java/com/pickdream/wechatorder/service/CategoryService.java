package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.beans.ProductCategory;

import java.util.List;

public interface CategoryService {

    ProductCategory findOne(Long categoryId);

    List<ProductCategory> getAll();

    List<ProductCategory> findByCategoryTypeIn(List<Long> categoryTypeIn);

    ProductCategory save(ProductCategory productCategory);
}
