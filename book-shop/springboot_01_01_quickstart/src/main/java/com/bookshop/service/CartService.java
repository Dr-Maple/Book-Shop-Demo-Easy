package com.bookshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookshop.domain.Cart;
import com.bookshop.domain.UserCart;

import java.util.List;

public interface CartService extends IService<Cart> {
    public List<Cart> getCurrentCustomerCart(String customer_id);
}
