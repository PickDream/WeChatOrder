package com.pickdream.wechatorder.controller;

import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categories = categoryService.findAll();
        map.put("categories",categories);
        return new ModelAndView("/category/list",map);
    }
}
