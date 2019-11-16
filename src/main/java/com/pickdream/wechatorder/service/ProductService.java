package com.pickdream.wechatorder.service;

import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {


    ProductInfo findOne(Long productId);

    /**
     * 获取所有上架商品，封装到ResultVO中
     * */
    ResultVo listUpProducts();

    /**
     * 增加删除库存
     * @param cartDTOList 用户订购商品与单价的列表对象
     * */
    void increaseStock(List<CartDTO> cartDTOList);
    void decreaseStock(List<CartDTO> cartDTOList);

    ProductInfo save(ProductInfo info);

    Page<ProductInfo> findAll(Pageable pageable);

    Page<ProductInfo> findByNameContains(Pageable pageable,String name);
    //上架
    ProductInfo onSale(Long productId);

    //下架
    ProductInfo offSale(Long productId);


}
