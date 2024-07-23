package com.bookshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Cart {
    @TableId(type = IdType.AUTO)
    private Long id;
    private int bookId;
    @TableField(exist = false)
    private String bookName;
    @TableField(exist = false)
    private double newPrice;
    @TableField(exist = false)
    private String imgUrl;
    private String customerId;
    private int num;
}
