package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.respository.ProductCategoryRepository;
import com.pickdream.wechatorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Long categoryId) {
        return repository.getOne(categoryId);
    }

    @Override
    public List<ProductCategory> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Long> categoryTypeIn) {
        return repository.findByCategoryTypeIn(categoryTypeIn);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
