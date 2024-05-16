package com.example.springboot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateTimeUtils {
    public static String getCurrentTimeString(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    public static String getCurrentTimeStringForTransactionID(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+String.valueOf(new Random().nextInt(90000) + 10000);
    }
}
