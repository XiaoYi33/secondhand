package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Category;
import com.example.springboot.mapper.CategoryMapper;
import org.springframework.stereotype.Service;


@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
}
