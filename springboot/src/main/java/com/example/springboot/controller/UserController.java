package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PutMapping("/update")
    public Result update(@RequestBody User user){
        user.setPassword(null);
        userService.updateById(user);
        return Result.success();
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestParam(required = true) Integer id,
                                 @RequestParam(required = true) String oldPassword,
                                 @RequestParam(required = true) String newPassword){
        userService.updatePassword(id, oldPassword, newPassword);
        return Result.success();
    }



    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username){
        System.out.println("username = " + username);
        User user=userService.selectByUsername(username);
        if(user==null){
            return Result.error("查无此人");
        }else {
            return Result.success(user);
        }
    }



}
