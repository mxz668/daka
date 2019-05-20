package com.daka.controller;

import com.daka.model.OrderModel;
import com.daka.model.ResponseModel;
import com.daka.model.UserModel;
import com.daka.service.OrderService;
import com.daka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/13 16:54
 * @Version 1.0
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("queryOrderList")
    public ResponseModel queryOrderList(@RequestBody OrderModel order){
        return orderService.queryOrderList(order);
    }

    @PostMapping("addOrder")
    public ResponseModel addOrder(@RequestBody OrderModel order){
        return orderService.addOrder(order);
    }
}
