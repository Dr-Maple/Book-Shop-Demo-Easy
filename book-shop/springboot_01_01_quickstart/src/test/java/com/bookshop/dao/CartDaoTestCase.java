package com.bookshop.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartDaoTestCase {
    @Autowired
    private CartDao cartDao;

    @Test
    void getCurrentCustomerCartTest(){
        System.out.println(cartDao.getCart("11111111111"));
    }
}
