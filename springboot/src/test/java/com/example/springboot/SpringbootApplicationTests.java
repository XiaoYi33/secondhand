package com.example.springboot;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.springboot.service.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SpringbootApplicationTests {
	@Resource
	private EmailService emailService;
	@Test
	void test() throws MessagingException {
		emailService.sendEmail("2678089155@qq.com","测试","测试测试");
	}
	@Test
	void contextLoads() {
	}

}
