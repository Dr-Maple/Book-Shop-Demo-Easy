package com.bookshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookshop.dao.OrdersDao;
import com.bookshop.domain.Books;
import com.bookshop.domain.Orders;
import com.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrderService {
    @Autowired
    private OrdersDao ordersDao;

    @Override
    public List<Orders> getOrdersInfoByStateAndCustomerId(int state, String customerId) {
        return ordersDao.getOrdersInfoByStateAndCustomerId(state, customerId);
    }

    @Override
    public List<Orders> selectAllOrdersByCustomerId(String customerId) {
        return ordersDao.selectAllOrdersByCustomerId(customerId);
    }

    @Override
    public List<Orders> selectAllOrders() {
        return ordersDao.selectAllOrders();
    }

    @Override
    public List<Orders> searchOrdersInfoByOrderIdOrCustomerId(String id, String customerId) {
        id = '%' + id + '%';
        customerId = '%' + customerId + '%';
        return ordersDao.searchOrdersInfoByOrderIdOrCustomerId(id, customerId);
    }

    @Override
    public void createOrders(Orders order) {
        ordersDao.insert(order);
        int sold = order.getNum();
        ordersDao.updateBooksInventory(sold, order.getBookId());
    }


}
