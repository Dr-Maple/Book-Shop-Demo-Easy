package com.bookshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrdersServiceTestCase {
    @Autowired
    private OrderService orderService;

    @Test
    public void test(){
        System.out.println(orderService.selectAllOrdersByCustomerId("11111111111"));
    }

}
