package com.example.springboot.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Product;
import com.example.springboot.entity.Transaction;
import com.example.springboot.entity.User;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.TransactionService;
import com.example.springboot.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author HKX
 * date: 2024-02-21 22:21
 * description: 管理员操作用户的控制类
 **/

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TransactionService transactionService;

    /**
     * 查询所有用户
     * @return
     */
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> userList=userService.list(new QueryWrapper<User>().orderByDesc("id"));//select * from user order by id desc
        return Result.success(userList);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User user=userService.getById(id);
        if(user==null){
            return Result.error("查无此人");
        }else {
            return Result.success(user);
        }
    }

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

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 根据id删除用户，删除用户同时删除对应商品和取消所有订单
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        userService.deleteUserById(id);

        return Result.success();
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids) throws Exception {
        for(Integer id:ids){
            userService.deleteUserById(id);
        }
        return Result.success();
    }



    /**
     * 多条件模糊查询用户信息
     * @param pageNumber
     * @param pageSize
     * @param username
     * @param nickname
     * @return
     */
    @GetMapping("/selectByPage")
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

    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody User user) throws MessagingException {
        userService.resetPasswordAdmin(user);
        return Result.success();
    }

}
