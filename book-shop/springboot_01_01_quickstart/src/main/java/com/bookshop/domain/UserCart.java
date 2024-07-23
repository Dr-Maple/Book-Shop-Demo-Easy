package com.bookshop.domain;

import lombok.Data;

@Data
public class UserCart {
    private Long cartId;
    private double newPrice;
    private String bookName;
    private String imgUrl;
    private int num;
}
