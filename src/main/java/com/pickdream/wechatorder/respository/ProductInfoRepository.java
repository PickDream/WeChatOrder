package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,Long> {
    List<ProductInfo> findByProductStatus(Integer status);

}
