package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.entity.Transaction;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
* @author HKX
* @description 针对表【order】的数据库操作Mapper
* @createDate 2024-02-27 21:42:54
* @Entity com.example.springboot.entity.Order
*/
public interface TransactionMapper extends BaseMapper<Transaction> {

    IPage<Map>  selectPageById(IPage<Map> page, String id);



    IPage<Map> selectAllInfoByBuyerId(IPage<Map> page, Integer userId,String transactionState);

    Transaction getOneByUserId(Integer userId);
}




