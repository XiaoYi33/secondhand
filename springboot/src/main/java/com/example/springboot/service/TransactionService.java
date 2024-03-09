package com.example.springboot.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Transaction;
import com.example.springboot.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
* @author HKX
* @description 针对表【order】的数据库操作Service
* @createDate 2024-02-27 21:42:54
*/
@Service
public class TransactionService extends ServiceImpl<TransactionMapper, Transaction> {
    @Autowired
    private TransactionMapper transactionMapper;

    public IPage<Map> selectPageById(Integer pageNumber, Integer pageSize, String id) {
        IPage<Map> page=new Page<>(pageNumber,pageSize);
        transactionMapper.selectPageById(page,id);
        return page;
    }

    public void deleteById(String id) {
        transactionMapper.deleteById(id);
    }

    public void createOrder(Transaction transaction) {
        transactionMapper.insert(transaction);
    }

    public Transaction getOneByUserId(Integer userId) {
        return transactionMapper.getOneByUserId(userId);
    }

    public IPage<Map> selectAllInfoByBuyerId(Integer pageNumber, Integer pageSize, Integer userId,String transactionState,String productName) {
        IPage<Map> page=new Page<>(pageNumber,pageSize);
        transactionMapper.selectAllInfoByBuyerId(page,userId,transactionState,productName);
        return page;
    }

    public Map selectDetailById(String id) {
        return transactionMapper.selectDetailById(id);
    }

    public IPage<Map> selectAllInfoBySellerId(Integer pageNumber, Integer pageSize, Integer userId, String transactionState,String productName) {
        IPage<Map> page=new Page<>(pageNumber,pageSize);
        transactionMapper.selectAllInfoBySellerId(page,userId,transactionState,productName);
        return page;
    }
}
