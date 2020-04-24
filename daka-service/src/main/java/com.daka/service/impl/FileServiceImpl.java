package com.daka.service.impl;

import com.daka.model.ResponseModel;
import com.daka.service.FileService;
import com.daka.util.OssFileUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/21 15:15
 * @Version 1.0
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public ResponseModel upload(InputStream inputStream, String fileName) {
        try {
            OssFileUtil.uploadFile(inputStream,fileName);
        }catch (Exception e){

        }
        return null;
    }
}
