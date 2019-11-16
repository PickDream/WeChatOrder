package com.pickdream.wechatorder.respository;

import com.pickdream.wechatorder.beans.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,Long> {

    List<ProductInfo> findByProductStatus(Integer status);

    Page<ProductInfo> findByProductNameContains(Pageable pageable, String name);
    

}
