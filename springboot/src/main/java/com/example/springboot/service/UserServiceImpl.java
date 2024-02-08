package com.example.springboot.service;

import cn.hutool.core.util.RandomUtil;
import com.example.springboot.common.Page;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * author HKX
 * date: 2024-01-30 16:21
 * description:
 **/
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public void deleteUsers(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.delete(id);
        }
    }

    @Override
    public List<User> selectAll() {
        List<User> userList = userMapper.selectAll();
        return userList;

    }

    @Override
    public User selectById(Integer id) {
        User user=userMapper.selectById(id);
        return user;
    }

    //根据username查询
    @Override
    public User selectByUsername(String username){
        User user=userMapper.selectByUsername(username);
        return user;
    }

    @Override
    public List<User> selectByMore(String username, String name) {
        List<User> userList=userMapper.selectByMore(username,name);
        return userList;
    }

    @Override
    public List<User> selectLike(String name) {
        List<User> userList=userMapper.selectLike(name);
        return userList;
    }

    @Override
    public Page<User> selectByPage(String name, Integer pageNumber, Integer pageSize) {
        Integer skipNumber=(pageNumber-1)*pageSize;

        Page<User> page=new Page<>();
        Integer total = userMapper.selectCountByPage(name);
        List<User> userList=userMapper.selectByPage(name,skipNumber,pageSize);
        page.setTotal(total);
        page.setList(userList);
        return page;
    }

    /**
     * 验证用户账户是否合法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {

        //根据用户名查询数据库用户信息
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if(dbUser==null){
            //抛出一个自定义的异常
            throw  new ServiceException("用户名或密码错误");//抛出异常，然后会被全局捕获器捕获到
        }
        if(!Objects.equals(user.getPassword(), dbUser.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }


        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    @Override
    public User register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if(dbUser!=null){
            //抛出一个自定义的异常
            throw  new ServiceException("用户名已存在");//抛出异常，然后会被全局捕获器捕获到
        }
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }

}

