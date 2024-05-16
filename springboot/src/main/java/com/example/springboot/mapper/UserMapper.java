package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;



public interface UserMapper extends BaseMapper<User> {
    void updatePasswordByUsername(String username,String password);
    User selectUserByNickname (String nickname);

}
