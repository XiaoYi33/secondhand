package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Product;
import com.example.springboot.entity.Transaction;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.ProductMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.*;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private EmailService emailService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private TransactionService transactionService;

    private Map<String, String> verificationCodes = new HashMap<>();

    /**
     * 重写save方法，为了保存的时候自动添加默认密码和昵称
     *
     * @param user
     * @return
     */
    @Override
    public boolean save(User user) {
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(MD5PasswordEncoder.encode("123456"));//新增用户密码为空则默认为123456
        }
        if (StrUtil.isBlank(user.getNickname())) {
            user.setNickname(user.getUsername());//新增用户昵称为空则默认为用户名
        }
        return super.save(user);
    }


    public User selectByUsername(String username) { //根据用户名查询封装成方法方便后续使用
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return getOne(queryWrapper);

    }

    /**
     * 验证用户账户是否合法
     *
     * @param user
     * @return
     */
    public User login(User user) {
        //根据用户名查询数据库用户信息
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser == null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户名或密码错误");//抛出异常，然后会被全局捕获器捕获到
        }
        if (!Objects.equals(MD5PasswordEncoder.encode(user.getPassword()), dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }


        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        dbUser.setPassword(null);
        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser != null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户名已存在");//抛出异常，然后会被全局捕获器捕获到
        }
        User dbUser1 = userMapper.selectUserByNickname(user.getNickname());
        if (dbUser1 != null) {
            throw new ServiceException("显示名已存在");
        }
        if(!user.getCode().equals(verificationCodes.get(user.getEmail()))){
            throw new ServiceException("验证码错误");
        }

        user.setPassword(MD5PasswordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        verificationCodes.remove(user.getEmail());//清除原有验证码
        user.setPassword(null);
        return user;
    }

    public String getCode(String username) throws MessagingException {
        User dbUser = selectByUsername(username);
        if (dbUser == null) {
            //抛出一个自定义的异常
            throw new ServiceException("用户不存在");//抛出异常，然后会被全局捕获器捕获到
        }
        String code = RandomNumber.generateRandomCode();//获取验证码
        String subject = "找回密码验证";
        emailService.sendEmail(dbUser.getEmail(), subject, HtmlGenerator.codeReminder(dbUser.getUsername(), code));
        verificationCodes.put(dbUser.getUsername(), code);
//        //dbUser.setPassword(MD5PasswordEncoder.encode("123456"));
//        updateById(dbUser);

        return dbUser.getEmail();
    }

    public void resetPassword(String username, String password, String code) {
        if (!code.equals(verificationCodes.get(username))) {//判断输入的验证码与之前生成的验证码是否一致
            throw new ServiceException("验证码错误");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5PasswordEncoder.encode(password));
        userMapper.updatePasswordByUsername(username, MD5PasswordEncoder.encode(password));
        verificationCodes.remove(username);//清除原有验证码
    }

    public void resetPasswordAdmin(User user) throws MessagingException {
        String password = RandomNumber.generateRandomPassword();
        user.setPassword(MD5PasswordEncoder.encode(password));
        userMapper.updateById(user);
        emailService.sendEmail(user.getEmail(), "重置密码成功", HtmlGenerator.resetPasswordReminder(user.getUsername(), password));
    }

    //删除用户时同时删除用户上架状态的商品，正在交易的订单改为已取消
    public void deleteUserById(Integer userId) throws Exception {
        //将交易中的订单改为已取消
        List<Transaction> transactionList = transactionService.getTransactionByUserId(userId);
        if (!transactionList.isEmpty()) {
            for (Transaction transaction : transactionList) {
                if (StrUtil.equals(transaction.getState(), "待交易")) {
                    transaction.setState("已取消");
                    transactionService.updateById(transaction);
                }
            }
        }
        //删除状态为上架的商品与商品图片
        List<Product> productList = productMapper.selectProductsByUserIdRetProduct(userId, "上架");
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                System.out.println(product);
                productMapper.deleteById(product.getId());
                DeleteFile.deleteImage(product.getImage());
            }
        }
        //删除用户头像以及用户
        User user = userMapper.selectById(userId);
        DeleteFile.deleteImage(user.getAvatar());
        userMapper.deleteById(userId);
    }

    public void updatePassword(Integer id,String oldPassword,String newPassword){
        User user = userMapper.selectById(id);
        if(user.getPassword().equals(MD5PasswordEncoder.encode(oldPassword))){
            userMapper.updatePasswordByUsername(user.getUsername(),MD5PasswordEncoder.encode(newPassword));
        }else {
            throw new ServiceException("原始密码不正确，请检查");
        }
    }

    public void getCodeForReg(String email) throws MessagingException {

        String code = RandomNumber.generateRandomCode();//获取验证码
        String subject = "注册账号验证";
        emailService.sendEmail(email, subject, HtmlGenerator.codeReminderForReg(code));
        verificationCodes.put(email, code);
    }

    public boolean updateById(User user) {
        System.out.println("user = " + user);
        User dbuser=userMapper.selectUserByNickname(user.getNickname());
        if(dbuser==null||Objects.equals(dbuser.getId(), user.getId())){
            return super.updateById(user);
        }else{
            throw new ServiceException("昵称重复");
        }
    }

}