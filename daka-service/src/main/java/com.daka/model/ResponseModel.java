package com.daka.model;

import java.io.Serializable;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/16 10:05
 * @Version 1.0
 */
public class ResponseModel implements Serializable {

    private String respCode = "0000";

    private String respMsg;

    private Object respData;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public Object getRespData() {
        return respData;
    }

    public void setRespData(Object respData) {
        this.respData = respData;
    }
}
