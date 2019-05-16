package com.daka.controller;

import com.daka.model.ResponseModel;
import com.daka.model.UserModel;
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
    private UserService userService;

    @PostMapping("queryOrderList")
    public ResponseModel queryOrderList(){
        ResponseModel responseModel = new ResponseModel();
        System.out.println("login");
        return responseModel;
    }

    @PostMapping("insertUser")
    public ResponseModel insertUser(@RequestBody UserModel user){
        ResponseModel responseModel = new ResponseModel();

        userService.insertUser(user);

        return responseModel;
    }
}
