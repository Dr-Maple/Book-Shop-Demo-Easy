package com.bookshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookshop.dao.CartDao;
import com.bookshop.domain.Cart;
import com.bookshop.domain.UserCart;
import com.bookshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartDao, Cart> implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public List<Cart> getCurrentCustomerCart(String customer_id) {
        return cartDao.getCart(customer_id);
    }
}
