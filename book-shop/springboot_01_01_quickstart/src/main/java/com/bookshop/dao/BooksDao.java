package com.bookshop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookshop.domain.Books;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BooksDao extends BaseMapper<Books> {

    @Select("select * from books where name like #{name}")
    List<Books> getByName(String name);

    @Select("select * from books where name like #{name} and author like #{author} and type = #{type}")
    List<Books> searchBookInfo(String name, String author, int type);

    @Select("select * from books where name like #{name} and author like #{author}")
    List<Books> searchBookInfoByNameAndAuthor(String name, String author);

    @Select("select * from books where type = #{type}")
    List<Books> selectBooksInfoByType(Integer type);

    @Select("update books set state =  #{state}")
    Boolean updateBookState(String state);


}
