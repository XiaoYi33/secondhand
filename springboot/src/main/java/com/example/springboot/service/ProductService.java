package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Product;
import com.example.springboot.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;

/**
 * author HKX
 * date: 2024-02-19 17:36
 * description:
 **/
@Service
public class ProductService extends ServiceImpl<ProductMapper,Product> {
    @Resource
    private ProductMapper productMapper;


    public Map selectProductDetailById(Integer id) {
        Map productMap = productMapper.queryDetailMap(id);
        return productMap;
    }

}
