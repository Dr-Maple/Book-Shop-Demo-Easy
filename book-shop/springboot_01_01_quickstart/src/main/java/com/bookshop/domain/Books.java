package com.bookshop.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Books {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String author;
    private double oldPrice;
    private double newPrice;
    private String description;
    private String imgUrl;
    private String date;
    private bookTypeEnum type;
    private Long inventory;
    private Long sold;
    private bookState state;
}
