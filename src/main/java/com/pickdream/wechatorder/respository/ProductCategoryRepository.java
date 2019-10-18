package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByCategoryTypeIn(List<Long> categoryTypeList);
}
