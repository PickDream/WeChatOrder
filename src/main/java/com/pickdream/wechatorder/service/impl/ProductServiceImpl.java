package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.VO.ProductInfoVo;
import com.pickdream.wechatorder.VO.ProductVo;
import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.enums.ProductStatusEnum;
import com.pickdream.wechatorder.respository.ProductCategoryRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCategoryRepository categoryRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ResultVo listUpProducts() {
        List<ProductInfo> productInfoList =
                productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
        List<Long> list = productInfoList
                .stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());
        List<ProductCategory> categories = categoryRepository.findByCategoryTypeIn(list);
        List<ProductVo> productVos = assembleProductsVo(productInfoList,categories);
        ResultVo resultVo = ResultVo.builder()
                .code(0)
                .message("成功")
                .data(productVos)
                .build();
        return resultVo;
    }

    private List<ProductVo> assembleProductsVo(List<ProductInfo> productInfos,
                                               List<ProductCategory> categories){
        List<ProductVo> productVoList = new ArrayList<>();
        Map<Long,ProductVo> productVoMap = new HashMap<>();
        categories.forEach((category)->{
            productVoMap.putIfAbsent(category.getCategoryId(),
                    ProductVo.builder().categoryName(category.getCategoryName())
                            .categoryType(category.getCategoryId())
                            .products(new ArrayList<ProductInfoVo>())
                            .build());
        });
        productInfos.forEach((productInfo)->{
            ProductInfoVo productInfoVo = new ProductInfoVo();
            BeanUtils.copyProperties(productInfo,productInfoVo);
            productVoMap.get(productInfo.getCategoryType())
                    .getProducts().add(productInfoVo);
        });
        productVoList.addAll(productVoMap.values());
        return productVoList;
    }
}
