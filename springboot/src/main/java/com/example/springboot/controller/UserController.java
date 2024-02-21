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

/**
 * author HKX
 * date: 2024-01-30 16:23
 * description:
 **/
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 新增用户信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        try {

            userService.save(user);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据错误");
            }else{
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.removeById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids){
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList=userService.list(new QueryWrapper<User>().orderByDesc("id"));//select * from user order by id desc
       return Result.success(userList);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user=userService.getById(id);
        if(user==null){
            return Result.error("查无此人");
        }else {
            return Result.success(user);
        }
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


    /**
     * 多条件模糊查询用户信息
     * @param pageNumber
     * @param pageSize
     * @param username
     * @param nickname
     * @return
     */
    @GetMapping("selectByPage")
    public Result selectByPage(@RequestParam Integer pageNumber,
                               @RequestParam Integer pageSize,
                               @RequestParam(required = false) String username,
                               @RequestParam(required = false) String nickname){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
        queryWrapper.like(StrUtil.isNotBlank(nickname),"nickname",nickname);
        //select * from user where username like '%#{username}%' and name like '%#{name}%'

        Page<User> page = userService.page(new Page<>(pageNumber, pageSize), queryWrapper);
        return Result.success(page);
    }

}
