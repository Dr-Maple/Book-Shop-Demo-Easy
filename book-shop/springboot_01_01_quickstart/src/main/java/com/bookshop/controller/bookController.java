package com.bookshop.controller;

import com.bookshop.controller.utils.R;
import com.bookshop.domain.Books;
import com.bookshop.domain.bookState;
import com.bookshop.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RestController
public class bookController {

    @Autowired
    private BooksService booksService;

    @PostMapping("/getAllBooks")
    public R getAllBooks(){
        System.out.println(booksService.list());
        return new R(true, booksService.list());
    }

    @PostMapping("/getBooksByName")
    public R getBooks(@RequestBody Map<String, String> bookNameData){
        System.out.println(booksService.getBooksByName(bookNameData.get("bookName")));
        return new R(true, booksService.getBooksByName(bookNameData.get("bookName")));
    }

    @PostMapping("/editBookInfo")
    public R editBookInfo(@RequestBody Books books){
        System.out.println(books.getName());
        return new R(booksService.saveOrUpdate(books));
    }

    @GetMapping("/searchBookInfo")
    public R searchBookInfo(@RequestParam("name") String name, @RequestParam("author") String author){
        System.out.println(booksService.searchBookInfoByNameAndAuthor(name, author));
        return new R(true, booksService.searchBookInfoByNameAndAuthor(name, author));

    }

    @GetMapping("/selectBooksInfoByType")
    public R selectBooksInfoByType(@RequestParam("bookType") Integer type){
        return new R(true, booksService.selectBooksInfoByType(type));
    }

    @PostMapping("/updateBookState")
    public R updateBookState(@RequestBody Books book){
        if (book.getState().getCode() == 1){
            book.setState(bookState.ON_SALE);
        }else if (book.getState().getCode() == 0){
            book.setState(bookState.NOT_ON_SALE);
        }
        return new R(booksService.updateById(book));
    }

    @PostMapping("/uploadBookImage")
    public void uploadBookImage(MultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            //将文件保存指定目录
            file.transferTo(new File("E:\\Mycode\\vue-smallFrog-bookMall-master\\static\\booksImage\\" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/bookInfo")
    public R bookInfo(@RequestBody Map<String, String> id){
        return new R(true, booksService.getById(id.get("id")));
    }
}
