package com.bookshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookshop.dao.BooksDao;
import com.bookshop.domain.Books;
import com.bookshop.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl extends ServiceImpl<BooksDao, Books> implements BooksService {
    @Autowired
    private BooksDao booksDao;

    @Override
    public List<Books> getBooksByName(String bookName) {
        bookName = '%' + bookName + '%';
        return booksDao.getByName(bookName);
    }

    @Override
    public List<Books> searchBookInfo(String name, String author, int type) {
        name = '%' + name + '%';
        author = '%' + author + '%';
        return booksDao.searchBookInfo(name, author, type);
    }

    @Override
    public List<Books> searchBookInfoByNameAndAuthor(String name, String author) {
        name = '%' + name + '%';
        author = '%' + author + '%';
        return booksDao.searchBookInfoByNameAndAuthor(name, author);
    }

    @Override
    public List<Books> selectBooksInfoByType(Integer type) {
        return booksDao.selectBooksInfoByType(type);
    }

    @Override
    public Boolean updateBookState(String state) {
        return booksDao.updateBookState(state);
    }
}
