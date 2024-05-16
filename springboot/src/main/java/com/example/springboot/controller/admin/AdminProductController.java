package com.example.springboot.controller.admin;

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
import com.example.springboot.utils.DeleteFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    /**
     * 更新商品信息，主要用在下架商品
     * @param product
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody Product product){
        product.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));//更新商品的update_time
        productService.updateById(product);
        return Result.success();
    }

    /**
     * 根据id删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        productService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除商品
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) throws Exception {
        productService.deleteByIds(ids);
        return Result.success();
    }


    /**
     * 通过用户名、用户昵称、商品ID、商品名来查询
     * 给商品管理页使用
     * @param pageNumber
     * @param pageSize
     * @param productId
     * @param username
     * @return
     */
    @GetMapping("/selectByParams")
    public Result selectByParams(@RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(required = false) Integer productId,
                                 @RequestParam(required = false) String username
    ){

        IPage<Map> page = productService.selectByParams(pageNumber, pageSize, productId, username);
        return Result.success(page);


    }
}
