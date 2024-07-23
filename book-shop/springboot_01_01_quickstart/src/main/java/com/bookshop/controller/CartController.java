package com.bookshop.controller;

import com.bookshop.controller.utils.R;
import com.bookshop.domain.Cart;
import com.bookshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/joinCart")
    public R bookJoinCart(@RequestBody Cart cart){
//        System.out.println(cartService.save(cart));
        return new R(cartService.save(cart));
    }

    @PostMapping("/Cart")
    public R getCurrentCustomerCart(@RequestBody Map<String, String> data){

        String customer_id = data.get("data");
//        System.out.println(cartService.getCurrentCustomerCart(customer_id));
        return new R(true, cartService.getCurrentCustomerCart(customer_id));
    }

    @PostMapping("/deleteCartBook")
    public R deleteBook(@RequestBody Map<String, String> data){
        return new R(true, cartService.removeById(data.get("cartId")));
    }

    @PostMapping("/deleteCartInfo")
    public R deleteCartInfo(@RequestBody Cart[] carts){
        for (Cart cart:carts){
            cartService.removeById(cart.getId());
        }
        return new R(true);
    }

    @PostMapping("/updateBookNum")
    public R updateBookNum(@RequestBody Cart cart){
        return new R(cartService.updateById(cart));
    }

}
