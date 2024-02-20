package com.example.springboot.service;

import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void login() {
        List<User> list = userMapper.selectList(null);
        list.forEach(System.out::println);
    }
}