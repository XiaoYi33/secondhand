package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName order
 */
@TableName(value ="transaction")
@Data
public class Transaction implements Serializable {
    private String id;

    private Integer buyerId;

    private Integer sellerId;

    private Integer productId;

    private Integer price;

    private String createTime;
    private String updateTime;

    private String state;
    private String comment;
    private Integer isShowedToBuyer;
    private Integer isShowedToSeller;

}