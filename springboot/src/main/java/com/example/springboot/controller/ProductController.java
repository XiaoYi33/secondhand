package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Product;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author HKX
 * date: 2024-02-19 17:34
 * description:
 **/
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 查询所有商品
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Product> productList = productService.list(new QueryWrapper<Product>());
        return  Result.success(productList);
    }

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(){
        return Result.success();
    }

}
