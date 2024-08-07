package com.example.springboot;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.example.springboot.entity.Transaction;
import com.example.springboot.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {
	@Autowired
	TransactionService transactionService;

	@Test
	void test(){

	}
	@Test
	void getOneByUserId() {
		List<Transaction> oneByUserId = transactionService.getTransactionByUserId(1);
		System.out.println("oneByUserId = " + oneByUserId);
	}

}
