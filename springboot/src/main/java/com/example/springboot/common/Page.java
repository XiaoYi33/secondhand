package com.example.springboot.common;

import lombok.Data;

import java.util.List;

/**
 * author HKX
 * date: 2024-01-30 23:17
 * description:
 **/
@Data
public class Page<T> {
    private Integer total;
    private List<T> list;
}
