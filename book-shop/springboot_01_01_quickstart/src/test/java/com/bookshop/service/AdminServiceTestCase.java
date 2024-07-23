package com.bookshop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTestCase {

    @Autowired
    private AdminService adminService;

    @Test
    void testGetByUsername(){
        System.out.println(adminService.getById("admin"));
    }

}
