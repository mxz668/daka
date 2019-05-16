package com.daka.service;

import com.daka.model.ResponseModel;
import com.daka.model.UserModel;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/13 16:54
 * @Version 1.0
 */
public interface UserService {

    ResponseModel insertUser(UserModel user);
}
