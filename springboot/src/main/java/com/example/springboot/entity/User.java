package com.example.springboot.entity;

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
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String role;
    private String signature;
    private String qq;
    private String wechat;
    private String phone;
    private String email;
    private String avatar;
    private String create_time;

    private String token;
}
