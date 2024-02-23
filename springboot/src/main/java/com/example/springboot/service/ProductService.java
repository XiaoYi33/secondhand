package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Product;
import com.example.springboot.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * author HKX
 * date: 2024-02-19 17:36
 * description:
 **/
@Service
public class ProductService extends ServiceImpl<ProductMapper,Product> {
    @Autowired
    private ProductMapper productMapper;


    @Override
    public Product getById(Serializable id) {
        return super.getById(id);
    }

}
