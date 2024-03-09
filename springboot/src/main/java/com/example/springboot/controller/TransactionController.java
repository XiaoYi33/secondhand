package com.example.springboot.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Product;
import com.example.springboot.entity.Transaction;
import com.example.springboot.entity.User;
import com.example.springboot.service.TransactionService;
import com.example.springboot.service.ProductService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

/**
 * author HKX
 * date: 2024-02-27 00:51
 * description: 订单控制类
 **/
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ProductService productService;

    /**
     * 创建订单接口，给home.vue用
     * @param transaction
     * @return
     */
    @PostMapping("/create")
    public Result create(@RequestBody Transaction transaction){
        Product product = productService.getById(transaction.getProductId());
        if(StrUtil.equals(product.getState(),"下架")){//验证商品是否为下架状态
            return Result.error("无法购买已下架商品");
        }
        if(transaction.getBuyerId()==product.getUserId()){
            return Result.error("无法购买自己发布的商品");
        }

        transaction.setId(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyyMMddHHmmss")+String.valueOf(new Random().nextInt(90000) + 10000));
        transactionService.createOrder(transaction);//创建订单

        product.setState("下架");
        product.setReason("卖出下架");
        product.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));
        productService.updateById(product);//更新商品信息

        return Result.success();
    }

    /**
     * 用户以买家身份，传入ID和订单状态，查询跟用户有关的所有订单跟商品信息
     * 用在purchasedProducts.vue
     *
     * @return
     */
    @GetMapping("/selectAllInfoByBuyerId")
    public Result selectAllInfoByBuyerId(@RequestParam Integer pageNumber,@RequestParam Integer pageSize,@RequestParam Integer userId,@RequestParam(required = false) String transactionState,@RequestParam(required = false) String productName){
        IPage<Map> page=transactionService.selectAllInfoByBuyerId(pageNumber,pageSize,userId,transactionState,productName);
        return Result.success(page);
    }

    /**
     * 完成交易接口，用在PurchasedProducts.vue
     * @param transaction
     * @return
     */
    @PutMapping("/finishTransaction")
    public Result finishTransaction(@RequestBody Transaction transaction){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户id
        Transaction dbTransaction = transactionService.getById(transaction.getId());//从数据库获取完整对象
        if(currentUser.getId()!=dbTransaction.getBuyerId()){
            return Result.error("无法操作他人订单");
        }
        dbTransaction.setState("已完成");
        dbTransaction.setComment("交易已完成");
        dbTransaction.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));
        transactionService.updateById(dbTransaction);
        return Result.success();
    }
    /**
     * 确认交易接口，用在PurchasedProducts.vue
     * @param transaction
     * @return
     */
    @PutMapping("/confirmTransaction")
    public Result confirmTransaction(@RequestBody Transaction transaction){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户id
        Transaction dbTransaction = transactionService.getById(transaction.getId());//从数据库获取完整对象
        if(currentUser.getId()!=dbTransaction.getSellerId()){
            return Result.error("无法操作他人订单");
        }
        dbTransaction.setState("待确认");
        dbTransaction.setComment("等待买家完成交易");
        dbTransaction.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));
        transactionService.updateById(dbTransaction);
        return Result.success();
    }

    /**
     * 取消订单接口，用在PurchasedProducts.vue
     * @param transaction
     * @return
     */
    @PutMapping("/cancelByBuyer")
    public Result cancelByBuyer(@RequestBody Transaction transaction){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户id
        Transaction dbTransaction = transactionService.getById(transaction.getId());//从数据库获取完整对象
        if(currentUser.getId()!=dbTransaction.getBuyerId()){
            return Result.error("无法操作他人订单");
        }
        dbTransaction.setState("已取消");
        dbTransaction.setComment("买家取消了订单");
        dbTransaction.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));
        transactionService.updateById(dbTransaction);
        return Result.success();
    }

    /**
     * 取消订单接口，用在PurchasedProducts.vue
     * @param transaction
     * @return
     */
    @PutMapping("/cancelBySeller")
    public Result cancelBySeller(@RequestBody Transaction transaction){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户id
        Transaction dbTransaction = transactionService.getById(transaction.getId());//从数据库获取完整对象
        if(currentUser.getId()!=dbTransaction.getSellerId()){
            return Result.error("无法操作他人订单");
        }
        dbTransaction.setState("已取消");
        dbTransaction.setComment("卖家取消了订单");
        dbTransaction.setUpdateTime(LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyy-MM-dd HH:mm:ss"));
        transactionService.updateById(dbTransaction);
        return Result.success();
    }


    /**
     * 买家删除订单，用在PurchasedProduct。vue
     * @param transaction
     * @return
     */
    @PutMapping("/buyerDelete")
    public Result buyerDelete(@RequestBody Transaction transaction){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户id
        Transaction dbTransaction = transactionService.getById(transaction.getId());//从数据库获取完整对象
        if(currentUser.getId()!=dbTransaction.getBuyerId()){
            return Result.error("无法操作他人订单");
        }
        dbTransaction.setIsShowedToBuyer(0);//逻辑删除
        transactionService.updateById(dbTransaction);
        return Result.success();
    }

    /**
     * 卖家删除订单，用在PurchasedProduct。vue
     * @param transaction
     * @return
     */
    @PutMapping("/sellerDelete")
    public Result sellerDelete(@RequestBody Transaction transaction){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户id
        Transaction dbTransaction = transactionService.getById(transaction.getId());//从数据库获取完整对象
        if(currentUser.getId()!=dbTransaction.getSellerId()){
            return Result.error("无法操作他人订单");
        }
        dbTransaction.setIsShowedToSeller(0);//逻辑删除
        transactionService.updateById(dbTransaction);
        return Result.success();
    }

    /**
     * 通过订单Id查询订单详细信息，用在PurchasedProduct。vue
     * @param id
     * @return
     */

    @GetMapping("/selectDetailById/{id}")
    public Result selectDetailById(@PathVariable String id){
        Map map=transactionService.selectDetailById(id);
        return Result.success(map);
    }

    /**
     * 用户以卖家身份传入ID，查询跟用户有关的所有订单跟商品信息
     * 用在soldProducts.vue
     * @param pageNumber
     * @param pageSize
     * @param userId
     * @param transactionState
     * @return
     */
    @GetMapping("/selectAllInfoBySellerId")
    public  Result selectAllInfoBySellerId(@RequestParam Integer pageNumber,@RequestParam Integer pageSize,@RequestParam Integer userId,@RequestParam(required = false) String transactionState,@RequestParam(required = false) String productName){
        IPage<Map> page=transactionService.selectAllInfoBySellerId(pageNumber,pageSize,userId,transactionState,productName);
        return Result.success(page);
    }
}
