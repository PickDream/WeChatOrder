package com.pickdream.wechatorder.service.impl;

import com.pickdream.wechatorder.VO.ProductInfoVo;
import com.pickdream.wechatorder.VO.ProductVo;
import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.dto.CartDTO;
import com.pickdream.wechatorder.enums.ExceptionEnum;
import com.pickdream.wechatorder.enums.ProductStatusEnum;
import com.pickdream.wechatorder.enums.ResultEnum;
import com.pickdream.wechatorder.exception.SellException;
import com.pickdream.wechatorder.respository.ProductCategoryRepository;
import com.pickdream.wechatorder.respository.ProductInfoRepository;
import com.pickdream.wechatorder.service.ProductService;
import com.pickdream.wechatorder.utils.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductCategoryRepository categoryRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;


    @Override
    public ProductInfo findOne(Long productId) {
        return productInfoRepository.getOne(productId);
    }

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
        return ResultVoUtil.success(productVos);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoRepository.getOne(cartDTO.getProductId());
            if (productInfo==null){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    //use default transactional propagation REQUIRED
    //TODO 可能会出现订单超卖
    public void decreaseStock(List<CartDTO> cartDTOList) {
        cartDTOList.stream().forEach((cartDTO)->{
            ProductInfo pInfo = productInfoRepository
                    .getOne(cartDTO.getProductId());
            if (Objects.isNull(pInfo)){
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            int result = pInfo.getProductStock()-cartDTO.getProductQuantity();
            if (result<0){
                throw new SellException(ExceptionEnum.PRODUCT_STOCK_ERROR);
            }
            pInfo.setProductStock(result);
            productInfoRepository.save(pInfo);
        });
    }

    @Override
    public ProductInfo save(ProductInfo info) {
        return productInfoRepository.save(info);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
       return productInfoRepository.findAll(pageable);
    }

    @Override
    public Page<ProductInfo> findByNameContains(Pageable pageable,String name) {
        return productInfoRepository.findByProductNameContains(pageable,name);
    }

    @Override
    public ProductInfo onSale(Long productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo == null) {
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ExceptionEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(Long productId) {
        ProductInfo productInfo = productInfoRepository.getOne(productId);
        if (productInfo == null) {
            throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ExceptionEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productInfoRepository.save(productInfo);
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
