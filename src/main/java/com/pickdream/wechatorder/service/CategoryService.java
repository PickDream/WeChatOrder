package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Long categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Long> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

    void delete(Long cid);

}
