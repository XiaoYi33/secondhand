package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.Product;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private CategoryService categoryService;

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
     * 分页查询，传入页码页数、商品名和商品分类
     * 先通过的categoryService查到对应的分类ID，再一起通过productService查到对应商品
     *
     * @return
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNumber,
                               @RequestParam Integer pageSize,
                               @RequestParam String productName,
                               @RequestParam String productCategory
                               ){
        Category category = categoryService.getOne(new QueryWrapper<Category>().eq("name", productCategory));
        Integer categoryId = category.getId();//获取传入productCategory对应的id

        QueryWrapper<Product> queryWrapper = new QueryWrapper<Product>().orderByDesc("update_time").eq("category_id",categoryId);;

        Page<Product> page=productService.page(new Page<>(pageNumber,pageSize),queryWrapper);
        return Result.success(page);
    }

}
