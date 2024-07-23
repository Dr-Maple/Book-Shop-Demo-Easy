package com.bookshop.domain;

import lombok.Data;

@Data
public class Customer {
    private String id;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String profilePhoto;
    private String description;
    private String member;
    private String memberDate;
    private String sex;
    private String birthday;
}
