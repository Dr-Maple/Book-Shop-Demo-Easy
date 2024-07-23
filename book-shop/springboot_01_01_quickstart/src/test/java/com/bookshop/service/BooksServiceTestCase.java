package com.bookshop.service;

import com.baomidou.mybatisplus.annotation.TableId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BooksServiceTestCase {
    @Autowired
    private BooksService booksService;

    @Test
    void getAllBooks(){
        System.out.println("ggggggg");
        System.out.println(booksService.list());
    }

    @Test
    void test(){
        System.out.println(booksService.searchBookInfoByNameAndAuthor("d", "b"));
    }
}
