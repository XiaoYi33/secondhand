package com.example.springboot.service;

import com.example.springboot.common.Page;
import com.example.springboot.entity.User;

import java.util.List;
import java.util.Map;

/**
 * author HKX
 * date: 2024-01-30 16:21
 * description:
 **/
public interface UserService {
    void insertUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    void deleteUsers(List<Integer> ids);

    List<User> selectAll();

    User selectById(Integer id);
    User selectByUsername(String username);

    List<User> selectByMore(String username, String name);

    List<User> selectLike(String name);

    Page<User> selectByPage(String name, Integer pageNumber, Integer pageSize);

    User login( User user);

    User register(User user);

    void resetPassword(User user);
}
