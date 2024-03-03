package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.controller.FileController;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.MD5PasswordEncoder;
import com.example.springboot.utils.RandomCode;
import com.example.springboot.utils.TokenUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * author HKX
 * date: 2024-01-30 16:21
 * description:
 **/
@Service
public class UserService extends ServiceImpl<UserMapper,User>{
    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailService emailService;

    private Map<String, String> verificationCodes = new HashMap<>();

    /**
     * 重写save方法，为了保存的时候自动添加默认密码和昵称
     * @param user
     * @return
     */
    @Override
    public boolean save(User user) {
        if(StrUtil.isBlank(user.getPassword())){
            user.setPassword(MD5PasswordEncoder.encode("123456"));//新增用户密码为空则默认为123456
        }
        if(StrUtil.isBlank(user.getNickname())){
            user.setNickname(user.getUsername());//新增用户昵称为空则默认为用户名
        }
        return super.save(user);
    }


    public User selectByUsername(String username){ //根据用户名查询封装成方法方便后续使用
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return getOne(queryWrapper);

    }

    /**
     * 验证用户账户是否合法
     * @param user
     * @return
     */
    public User login(User user) {
        //根据用户名查询数据库用户信息
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser==null){
            //抛出一个自定义的异常
            throw  new ServiceException("用户名或密码错误");//抛出异常，然后会被全局捕获器捕获到
        }
        if(!Objects.equals(MD5PasswordEncoder.encode(user.getPassword()), dbUser.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }


        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        dbUser.setPassword("");
        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser!=null){
            //抛出一个自定义的异常
            throw  new ServiceException("用户名已存在");//抛出异常，然后会被全局捕获器捕获到
        }
        user.setPassword(MD5PasswordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    public String getCode(String username) throws MessagingException {
        User dbUser = selectByUsername(username);
        if(dbUser==null){
            //抛出一个自定义的异常
            throw  new ServiceException("用户不存在");//抛出异常，然后会被全局捕获器捕获到
        }
        String code = RandomCode.generateRandomCode();//获取验证码
        String subject = "找回密码验证";
        String text = "您的验证码是:"+code;
        emailService.sendEmail(dbUser.getEmail(),subject,text);
        verificationCodes.put(dbUser.getUsername(),code);
//        //dbUser.setPassword(MD5PasswordEncoder.encode("123456"));
//        updateById(dbUser);

        return dbUser.getEmail();
    }
    public void resetPassword(String username, String password,String code){
        if(!code.equals(verificationCodes.get(username))){//判断输入的验证码与之前生成的验证码是否一致
            throw new ServiceException("验证码错误");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5PasswordEncoder.encode(password));
        //差一个根据username更新数据库的方法
    }
}

