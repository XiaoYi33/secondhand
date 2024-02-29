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

    private Integer userId;

    private Integer productId;

    private Integer createTime;

    private String state;

}