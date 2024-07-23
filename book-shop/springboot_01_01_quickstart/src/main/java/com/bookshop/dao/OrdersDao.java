package com.bookshop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookshop.domain.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersDao extends BaseMapper<Orders> {
    @Select("select orders.*, books.name bookName, books.img_url bookImgUrl, books.author bookAuthor from orders,books where orders.state = #{state} and books.id = orders.book_id and orders.customer_id = #{customerId}")
    List<Orders> getOrdersInfoByStateAndCustomerId(int state, String customerId);

    @Select("select orders.*, books.name bookName, books.img_url bookImgUrl, books.author bookAuthor from orders,books where orders.customer_id = #{customerId} and books.id = orders.book_id")
    List<Orders> selectAllOrdersByCustomerId(String customerId);

    @Select("select orders.*, books.name bookName, books.author bookAuthor, customer.username customerName, books.img_url bookImgUrl, books.type bookType from orders, books, customer where orders.customer_id = customer.id and books.id = orders.book_id")
    List<Orders> selectAllOrders();

    @Select("select orders.*, books.name bookName, books.author bookAuthor, customer.username customerName, books.img_url bookImgUrl, books.type bookType from orders, books, customer where orders.id like #{id} and customer.id like #{customerId} and orders.customer_id = customer.id and books.id = orders.book_id")
    List<Orders> searchOrdersInfoByOrderIdOrCustomerId(String id, String customerId);

    @Select("update books set inventory = inventory-#{sold}, sold = sold+ #{sold} where id = #{id}")
    void updateBooksInventory(int sold, Long id);
}
