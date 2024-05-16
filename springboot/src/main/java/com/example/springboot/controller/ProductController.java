package com.example.springboot.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.Product;
import com.example.springboot.entity.User;
import com.example.springboot.service.CategoryService;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.DateTimeUtils;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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

    /**
     * 更新商品接口，用在myProducts.vue
     * @param product
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Product product){
        User currentUser = TokenUtils.getCurrentUser();//获取传进token的用户id
        if(product.getUserId()!=currentUser.getId()){//将当前用户id和商品的用户id进行比较
            return Result.error("无法编辑他人商品");
        }
        if(StrUtil.equals("下架",product.getState())){
            return Result.error("已下架商品无法编辑");
        }
        product.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));//更新商品的update_time
        productService.updateById(product);
        return Result.success();
    }

    @Deprecated
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
     * 查询单个商品，给ProductDetail.vue用
     * @param id
     * @return
     */
    @GetMapping("/selectProductDetailById/{id}")
    public Result selectProductDetailById(@PathVariable Integer id){
        Map product=productService.selectProductDetailById(id);
        return Result.success(product);
    }


    /**
     * 根据ID删除商品，用在myProducts.vue
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        User currentUser = TokenUtils.getCurrentUser();//获取传进token的用户id
        Product product = productService.getById(id);
        if(product.getUserId()!=currentUser.getId()){//将当前用户id和商品的用户id进行比较
            return Result.error("无法删除他人商品");
        }
        productService.removeById(id);
        return Result.success();
    }

    @PostMapping("/add")
    public Result add(@RequestBody Product product){
        product.setCreateTime(DateTimeUtils.getCurrentTimeString());
        productService.save(product);
        return Result.success();
    }

    /**
     * 分页查询，传入页码页数、商品名和商品分类
     * 先通过的categoryService查到对应的分类ID，再一起通过productService查到对应商品
     *
     * 给主页和搜索框用
     *
     * 已废弃
     * @return
     */
    @Deprecated
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

    /**
     * 代替/selectByPage接口，用在home.vue和Manager.vue的搜索框
     * @param pageNumber
     * @param pageSize
     * @param productName
     * @param productCategory
     * @return
     */
    @GetMapping("/selectProductsByPage")
    public Result selectProductsByPage(@RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(required = false) String productName,
                                 @RequestParam(required = false) String productCategory){
        IPage<Map> page = productService.selectPageByNameOrCategory(pageNumber, pageSize, productName, productCategory);
        return Result.success(page);
    }


    /**
     * 根据用户Id查商品，用在myProducts.vue
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @param productState
     * @return
     */
    @GetMapping("/selectProductsByUserId")
    public Result selectProductsByUserId(@RequestParam Integer pageNumber,
                                         @RequestParam Integer pageSize,
                                         @RequestParam Integer userId,
                                         @RequestParam String productState){
        IPage<Map> page = productService.selectProductsByUserId(pageNumber, pageSize, userId, productState);
        return Result.success(page);
    }


    /**
     * 用户商品下架接口，用于myProducts.vue
     * @return
     */
    @PutMapping("/takeDown")
    public Result takeDown(@RequestParam Integer productId){
        User currentUser = TokenUtils.getCurrentUser();//获取传进token的用户id
        Product product = productService.getById(productId);
        if(product.getUserId()!=currentUser.getId()){//将当前用户id和商品的用户id进行比较
            return Result.error("无法编辑他人商品");
        }
        product.setState("下架");
        product.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));//更新商品的update_time
        product.setReason("用户下架");
        productService.updateById(product);
        return Result.success();
    }



}
