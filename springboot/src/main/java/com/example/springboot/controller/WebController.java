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

/**
 * author HKX
 * date: 2024-01-30 14:16
 * description: 提供接口返回数据
 **/
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
            //todo 这里修改注册界面的时候要改校验规则
            return Result.error("数据输入不合法");
        }
        //todo：这里可以校验一下输入用户名的规则，前后端一起做校验
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
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestParam String username, @RequestParam String password, @RequestParam String code) {
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(code)) {
            return Result.error("数据输入不合法");
        }
        userService.resetPassword(username, password, code);
        return Result.success();
    }
}
