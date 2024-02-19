package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author HKX
 * date: 2024-01-30 16:15
 * description: 用户实体类
 **/
@Data
@AllArgsConstructor //所有属性的构造方法
@NoArgsConstructor //无参构造方法
@TableName("user") //对应数据库表名称
public class User {
    @TableId(type=IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String wechat;
    private String phone;
    private String email;
    private String avatar;
    private String createTime;

    @TableField(exist = false)//数据库不存在这个字段
    private String token;
}
