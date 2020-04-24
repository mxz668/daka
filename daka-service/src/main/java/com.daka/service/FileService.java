package com.daka.service;

import com.daka.model.ResponseModel;

import java.io.InputStream;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/21 15:12
 * @Version 1.0
 */
public interface FileService {

    ResponseModel upload(InputStream inputStream, String fileName);
}
