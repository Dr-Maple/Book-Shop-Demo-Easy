package com.bookshop.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrdersDaoTestCase {
    @Autowired
    private OrdersDao ordersDao;

    @Test
    public void test(){
        System.out.println(ordersDao.selectAllOrdersByCustomerId("11111111111"));
    }

}
