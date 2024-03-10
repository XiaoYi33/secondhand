package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.Product;

import java.util.Map;

public interface ProductMapper extends BaseMapper<Product> {

    Map queryDetailMap(Integer id);

    IPage<Map> selectPageByNameOrCategory(IPage page,String productName,String productCategory);

    IPage<Map> selectByParams(IPage page,Integer productId,String username);

    IPage<Map> selectProductsByUserId(IPage page, Integer userId, String productState);

    void deleteByUserId(Integer userId);
}
