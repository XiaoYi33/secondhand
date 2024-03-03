package com.example.springboot.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.common.Result;
import com.example.springboot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * author HKX
 * date: 2024-02-27 01:15
 * description: 管理员操作订单控制类
 **/

@RestController
@RequestMapping("/admin/transaction")
public class AdminTransactionController {
    @Autowired
    private TransactionService transactionService;

    /**
     * Order.vue订单管理页用，
     * 可通过ID来查询
     * @param pageNumber
     * @param pageSize
     * @param id
     * @return
     */
    @GetMapping("/selectPageById")
    public Result selectPageById(@RequestParam Integer pageNumber,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(required = false) String id){
        IPage<Map> page= transactionService.selectPageById(pageNumber,pageSize,id);
        return Result.success(page);

    }

    /**
     * 通过订单id删除，order是关键字所以重写了mapper里的deleteById方法
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id){
        transactionService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<String> ids){
        transactionService.removeBatchByIds(ids);
        return Result.success();
    }

}
