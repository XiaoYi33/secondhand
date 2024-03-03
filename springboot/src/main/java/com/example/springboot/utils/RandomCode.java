package com.example.springboot.utils;

import java.util.Random;

public class RandomCode {
    public static String generateRandomCode() {
        // 随机生成六位数的验证码
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
