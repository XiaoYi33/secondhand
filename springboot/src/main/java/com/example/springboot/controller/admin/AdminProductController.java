package com.example.springboot.controller.admin;

import cn.hutool.core.date.LocalDateTimeUtil;
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

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * author HKX
 * date: 2024-02-22 00:26
 * description: 管理员操作商品的控制类
 **/
@RestController
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Product product){
//        product.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));//更新商品的update_time
        productService.updateById(product);
        return Result.success();
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        productService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids){
        productService.removeBatchByIds(ids);
        return Result.success();
    }


    /**
     * 通过用户名、用户昵称、商品ID、商品名来查询
     * 给商品管理页使用
     * @param pageNumber
     * @param pageSize
     * @param productId
     * @param productName
     * @param username
     * @return
     */
    @GetMapping("/selectByParams")
    public Result selectByParams(@RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(required = false) Integer productId,
                                 @RequestParam(required = false) String productName,
                                 @RequestParam(required = false) String username
    ){

        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.orderByDesc("id");
        productQueryWrapper.eq(productId!=null,"id",productId);
        productQueryWrapper.like(StrUtil.isNotBlank(productName),"name",productName);

        if(StrUtil.isNotBlank(username)){
            User user = userService.getOne(new QueryWrapper<User>().eq("username",username));
            if(user!=null){
                productQueryWrapper.eq("user_id",user.getId());
            }
        }
        Page<Product> page=productService.page(new Page<>(pageNumber,pageSize),productQueryWrapper);

        for(Product product:page.getRecords()){
            product.setUser(userService.getOne(new QueryWrapper<User>().eq("id",product.getUserId())));
            product.setCategory(categoryService.getOne(new QueryWrapper<Category>().eq("id",product.getCategoryId())));
        }

        return Result.success(page);

    }
}
