package com.example.springboot.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HtmlGenerator {

    private static String url; // 将url字段改为静态字段

    @Value("${myapp.global.staticVar.url}")
    public void setUrl(String url) {
        HtmlGenerator.url = url;
    }

    //验证码提醒
    public static String codeReminder(String username,String code){
        return "<html><body>" +
                "<h2>找回密码验证</h2>" +
                "<p>尊敬的:<b>" + username + "</b>，您的验证码是：</p>" +
                "<h1 style='font-size: 32px; color: #FF0000;'>" + code + "</h1>" +
                "</body></html>";
    }
    public static String codeReminderForReg(String code){
        return "<html><body>" +
                "<h2>注册账号验证</h2>" +
                "<p>感谢您注册本系统，您的验证码是：</p>" +
                "<h1 style='font-size: 32px; color: #FF0000;'>" + code + "</h1>" +
                "</body></html>";
    }

    //卖出提醒
    public static String sellReminder(String username,String name){
        return "<html><body>" +
                "<p>尊敬的:<b>" + username + "</b></p>" +
                "<p>您的商品:<b>" + name + "</b>已卖出，请及时登录系统与买家联系</p>" +
                "<p>请点击链接登录系统：<a href=\"" + url + "\">登录系统</a></p>" +
                "</body></html>";
    }

    //重置密码提醒
    public static String resetPasswordReminder(String username,String password){
        System.out.println(url);
        return "<html><body>" +
                "<p>尊敬的:<b>" + username + "</b>，您的密码已重置为：</p>" +
                "<h1 style='font-size: 32px; color: #FF0000;'>" + password + "</h1>" +
                "<p>请点击链接登录系统：<a href=\"" + url + "\">登录系统</a></p>" +
                "</body></html>";
    }
}
