package com.daka.service.impl;

import com.daka.mapper.UserMapper;
import com.daka.model.ResponseModel;
import com.daka.model.UserModel;
import com.daka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/16 15:44
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseModel insertUser(UserModel user) {
        ResponseModel responseModel = new ResponseModel();

        userMapper.insertUser(user);

        return responseModel;
    }
}
