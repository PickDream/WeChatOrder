package com.pickdream.wechatorder.controller;

import com.pickdream.wechatorder.beans.ProductCategory;
import com.pickdream.wechatorder.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @GetMapping("del")
    public ModelAndView del(Long cid,Map<String,Object> map){
        categoryService.delete(cid);
        map.put("url","/sell/seller/category/list");
        map.put("msg","删除品类成功");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("update")
    public ModelAndView update(@RequestParam(required = false) Long cid, Map<String,Object> map){
        if (!Objects.isNull(cid)){
            ProductCategory category = categoryService.findOne(cid);
            map.put("category",category);
        }
        return new ModelAndView("category/index",map);
    }

    @GetMapping("save")
    public ModelAndView save(@RequestParam(required = false) Long cid,String cname ){
        ProductCategory category = new ProductCategory();
        category.setCategoryId(cid);
        category.setCategoryName(cname);
        category.setCategoryType(0L);
        categoryService.save(category);
        return new ModelAndView("redirect:/seller/category/list");
    }
}
