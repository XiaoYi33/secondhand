package com.example.springboot.controller;

import com.example.springboot.common.Page;
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
            userService.insertUser(user);
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
        userService.updateUser(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteUser(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids){
        userService.deleteUsers(ids);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList=userService.selectAll();
       return Result.success(userList);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        System.out.println("查询的id为" + id);
        User user=userService.selectById(id);
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

    @GetMapping("/selectByMore")
    public Result selectByMore(@RequestParam String username,@RequestParam String name){
        List<User> userList=userService.selectByMore(username,name);
        return Result.success(userList);
    }

    @GetMapping("/selectLike")
    public Result selectLike(@RequestParam String name){
        List<User> userList=userService.selectLike(name);
        return Result.success(userList);
    }

    @GetMapping("selectByPage")
    public Result selectByPage(@RequestParam String name,
                               @RequestParam Integer pageNumber,
                               @RequestParam Integer pageSize){
        Page<User> result = userService.selectByPage(name, pageNumber, pageSize);
        return Result.success(result);
    }

}
