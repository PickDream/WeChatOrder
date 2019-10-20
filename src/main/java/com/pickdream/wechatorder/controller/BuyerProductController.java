package com.pickdream.wechatorder.controller;

import com.pickdream.wechatorder.VO.ResultVo;
import com.pickdream.wechatorder.beans.ProductInfo;
import com.pickdream.wechatorder.service.CategoryService;
import com.pickdream.wechatorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    ProductService productService;
    @GetMapping("/list")
    public ResultVo list(){
        return productService.listUpProducts();
    }
}
