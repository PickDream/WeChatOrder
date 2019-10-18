package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void setProductCategory(){
        ProductCategory category = ProductCategory.builder()
                .categoryName("随便").categoryType(2).build();
        repository.save(category);
        log.info("category:{}",category);
    }
}