package com.bookshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookshop.domain.Orders;

import java.util.List;

public interface OrderService extends IService<Orders> {
    List<Orders> getOrdersInfoByStateAndCustomerId(int state,String customerId);
    List<Orders> selectAllOrdersByCustomerId(String customerId);
    List<Orders> selectAllOrders();
    List<Orders> searchOrdersInfoByOrderIdOrCustomerId(String bookName, String bookAuthor);
    void createOrders(Orders order);
}
