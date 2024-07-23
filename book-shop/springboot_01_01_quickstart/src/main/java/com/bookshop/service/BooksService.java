package com.bookshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookshop.domain.Books;

import java.util.List;

public interface BooksService extends IService<Books> {

    List<Books> getBooksByName(String bookName);

    List<Books> searchBookInfo(String name, String author, int type);

    List<Books> searchBookInfoByNameAndAuthor(String name, String author);

    List<Books> selectBooksInfoByType(Integer type);

    Boolean updateBookState(String state);
}
