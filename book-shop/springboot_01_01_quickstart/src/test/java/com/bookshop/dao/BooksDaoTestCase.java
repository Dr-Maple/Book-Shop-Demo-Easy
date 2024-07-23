package com.bookshop.dao;

import com.bookshop.domain.Books;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BooksDaoTestCase {
    @Autowired
    private BooksDao booksDao;

    @Test
    void selectBook(){
        System.out.println(booksDao.getByName("%d%"));
    }

    @Test
    void getAllBooks(){
        System.out.println(booksDao.selectById(1));
    }
}
