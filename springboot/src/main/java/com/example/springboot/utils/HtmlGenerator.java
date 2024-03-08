package com.example.springboot.utils;

public class HtmlGenerator {

    public static String codeText(String username,String code){
        return "<html><body>" +
                "<h2>找回密码验证</h2>" +
                "<p>尊敬的:<b>" + username + "</b>，您的验证码是：</p>" +
                "<h1 style='font-size: 32px; color: #FF0000;'>" + code + "</h1>" +
                "</body></html>";
    }
    public static String sellOutReminder(String username,String name){
        return "<html>" +
                "<head>" +
                "<style>" +
                "h1 {" +
                "color: #FF0000;" +
                "font-weight: bold;" +
                "}" +
                "p {" +
                "color: #0000FF;" +
                "font-weight: bold;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<h1>亲爱的<span style=\"color: #00FF00;\">"+username+"</span>：</h1>" +
                "<p>您的商品<span style=\"color: #FF00FF;\">"+ name +"</span>已卖出，请及时登录系统处理</p>" +
                "<p>链接：<a href=\"此处放链接\" style=\"color: #0000FF; font-weight: bold;\">xxxx</a></p>" +
                "</body>" +
                "</html>";
    }
}
