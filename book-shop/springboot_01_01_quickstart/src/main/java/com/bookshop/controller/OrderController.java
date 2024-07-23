package com.bookshop.controller;

import com.bookshop.controller.utils.R;
import com.bookshop.domain.Orders;
import com.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/getAllOrders")
    public R selectAllOrders(@RequestBody Map<String, String> customerId){
        System.out.println(customerId.get("customerId"));
        return new R(true, orderService.selectAllOrdersByCustomerId(customerId.get("customerId")));
    }

    @PostMapping("/getAllOrdersInfo")
    public R getAllOrdersInfo(){
        System.out.println(orderService.selectAllOrders());
        return new R(true, orderService.selectAllOrders());
    }

    @GetMapping("/getOrdersInfoByStateAndCustomerId")
    public R getOrdersInfoByStateAndCustomerId(@RequestParam("state") Integer state, @RequestParam("customerId") String customerId){
        return new R(true,orderService.getOrdersInfoByStateAndCustomerId(state, customerId));
    }

    @PostMapping("/createOrders")
    public R createOrders(@RequestBody Orders[] ordersList){

        for (Orders order: ordersList){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setDate(df.format(new Date()));
            System.out.println(order);
            orderService.createOrders(order);
        }
        return new R(true);
    }

    @PostMapping("/editOrderInfo")
    public R editOrderInfo(@RequestBody Orders orders){
        return new R(orderService.updateById(orders));
    }

    @GetMapping("/searchOrdersInfoByOrderIdOrCustomerId")
    public R searchOrdersInfoByOrderIdOrCustomerId(@RequestParam("id") String id, @RequestParam("CustomerId") String CustomerId){
        return new R(true, orderService.searchOrdersInfoByOrderIdOrCustomerId(id, CustomerId));
    }
}
