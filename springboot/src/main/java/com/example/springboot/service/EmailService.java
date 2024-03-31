package com.example.springboot.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmailService {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.nickname}")
    private String nickname;

    public void sendEmail(String to,String subject ,String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
        mimeMessageHelper.setFrom(nickname+'<'+from+'>');
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text,true);//第二个参数加true可发送html格式
        javaMailSender.send(message);
    }

}
