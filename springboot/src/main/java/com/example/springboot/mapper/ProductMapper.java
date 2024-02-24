package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.Product;

import java.util.Map;

public interface ProductMapper extends BaseMapper<Product> {

    Map queryDetailMap(Integer id);

}
