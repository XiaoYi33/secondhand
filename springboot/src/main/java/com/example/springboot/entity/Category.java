package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * author HKX
 * date: 2024-02-20 22:57
 * description: 类别实体类
 **/

@Data
@TableName("category")
public class Category {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String name;
}
