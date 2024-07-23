package com.bookshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceTestCase {
    @Autowired
    private CartService cartService;

    @Test
    void test(){
        System.out.println(cartService.getCurrentCustomerCart("11111111111"));
    }
}
