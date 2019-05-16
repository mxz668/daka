package com.daka.model;

import java.io.Serializable;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2019/5/16 15:39
 * @Version 1.0
 */
public class UserModel implements Serializable {

    private String userName;
    private String pass;
    private String mobile;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
