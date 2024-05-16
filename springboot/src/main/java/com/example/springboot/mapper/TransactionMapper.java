package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.Transaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;


public interface TransactionMapper extends BaseMapper<Transaction> {

    IPage<Map>  selectPageById(IPage<Map> page, String id);



    IPage<Map> selectAllInfoByBuyerId(IPage<Map> page, Integer userId,String transactionState,String productName);

    List<Transaction> getTransactionByUserId(Integer userId);

    Map selectDetailById(String id);

    IPage<Map> selectAllInfoBySellerId(IPage<Map> page, Integer userId, String transactionState,String productName);
}




