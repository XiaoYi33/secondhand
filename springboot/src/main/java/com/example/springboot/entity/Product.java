package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author HKX
 * date: 2024-02-19 00:25
 * description: 商品类
 **/

@Data
@AllArgsConstructor //所有属性的构造方法
@NoArgsConstructor //无参构造方法
@TableName("product")
public class Product {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private String image;
    private Integer categoryId;
    private Double price;
    private Integer quantity;
    private Integer userId;
    private String state;
    private String createTime;
    private String updateTime;


}
