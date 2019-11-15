package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.VO.ProductInfoVo;
import com.pickdream.wechatorder.VO.ProductVo;
import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.enums.ProductStatusEnum;
import com.pickdream.wechatorder.respository.ProductCategoryRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Long categoryId) {
        return repository.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Long> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Override
    public void delete(Long cid) {
        repository.deleteById(cid);
    }
}
