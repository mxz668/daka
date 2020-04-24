package com.daka.controller;

import com.daka.model.ResponseModel;
import com.daka.model.UserModel;
import com.daka.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/13 16:54
 * @Version 1.0
 */
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public ResponseModel upload(HttpServletRequest request){
        ResponseModel responseModel = new ResponseModel();
        System.out.println("login");
        return responseModel;
    }
}
