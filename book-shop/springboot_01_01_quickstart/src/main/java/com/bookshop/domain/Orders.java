package com.bookshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Orders {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long bookId;
    @TableField(exist = false)
    private String bookName;
    @TableField(exist = false)
    private String bookImgUrl;
    @TableField(exist = false)
    private String bookAuthor;
    @TableField(exist = false)
    private bookTypeEnum bookType;
    private String customerId;
    @TableField(exist = false)
    private String customerName;
    private String address;
    private String receiver;
    private String tel;
    private int num;
    private double singlePrice;
    private double totalPrice;
    private orderState state;
    private String date;
    private String endDate;
}
