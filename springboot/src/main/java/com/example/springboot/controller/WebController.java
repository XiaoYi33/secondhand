package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.AuthAccess;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
public class WebController {
    @Autowired
    private UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result hello() {
        return Result.success("success");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) ||
                StrUtil.isBlank(user.getPassword()) ||
                StrUtil.isBlank(user.getCode()) ||
                StrUtil.isBlank(user.getNickname()) ||
                StrUtil.isBlank(user.getEmail())) {
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return Result.success(user);
    }

    @AuthAccess
    @GetMapping("/getCodeForReg/{email}")
    public Result getCodeForReg(@PathVariable String email) throws MessagingException {
        if (StrUtil.isBlank(email)) {
            return Result.error("数据输入不合法");
        }
        userService.getCodeForReg(email);
        return Result.success();
    }

    /**
     * 重置密码
     *
     * @return
     */
    @AuthAccess
    @GetMapping("/getCode/{username}")
    public Result getCode(@PathVariable String username) throws MessagingException {
        if (StrUtil.isBlank(username)) {
            return Result.error("数据输入不合法");
        }
        String email = userService.getCode(username);
        return Result.success(email);
    }

    @AuthAccess
    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getCode())) {
            return Result.error("数据输入不合法");
        }
        userService.resetPassword(user.getUsername(), user.getPassword(), user.getCode());
        return Result.success();
    }
}
