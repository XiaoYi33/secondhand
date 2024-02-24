package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.Product;
import com.example.springboot.entity.User;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private UserService userService;

    /**
     * 查询所有商品
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<Product> productList = productService.list(new QueryWrapper<Product>());
        return  Result.success(productList);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){

        Product product = productService.getById(id);
        if(product==null){
            return Result.error("查无此商品");
        }else{
            product.setUser(userService.getById(product.getUserId()));
            product.setCategory(categoryService.getById(product.getCategoryId()));
            return Result.success(product);
        }
    }


    /**
     * 查询单个商品，给ProductDetail.vue用（还没用）
     * @param id
     * @return
     */
    @GetMapping("/selectProductDetailById/{id}")
    public Result selectProductDetailById(@PathVariable Integer id){
        Map product=productService.selectProductDetailById(id);
        return Result.success(product);
    }



    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        productService.removeById(id);
        return Result.success();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Product product){
        productService.save(product);
        return Result.success();
    }

    /**
     * 分页查询，传入页码页数、商品名和商品分类
     * 先通过的categoryService查到对应的分类ID，再一起通过productService查到对应商品
     *
     * 给主页和搜索框用
     * @return
     */
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNumber,
                               @RequestParam Integer pageSize,
                               @RequestParam(required = false) String productName,
                               @RequestParam(required = false) String productCategory
                               ){
        Integer categoryId=null;

        if(StrUtil.isNotBlank(productCategory)){
            Category category = categoryService.getOne(new QueryWrapper<Category>().eq("name", productCategory));
            categoryId = category.getId();//获取传入productCategory对应的id
        }

        QueryWrapper<Product> queryWrapper = new QueryWrapper<Product>().orderByDesc("update_time");
        queryWrapper.eq(StrUtil.isNotBlank(productCategory),"category_id",categoryId);//如传入productCategory为空则不执行
        queryWrapper.like(StrUtil.isNotBlank(productName),"name", productName);//如传入productName则不执行
        queryWrapper.eq("state","上架");
        Page<Product> page=productService.page(new Page<>(pageNumber,pageSize),queryWrapper);

        for(Product product:page.getRecords()){
            product.setUser(userService.getOne(new QueryWrapper<User>().eq("id",product.getUserId())));
            product.setCategory(categoryService.getOne(new QueryWrapper<Category>().eq("id",product.getCategoryId())));
        }
        
        return Result.success(page);

    }



}
