package com.daka.util.evenBus;

import com.google.common.eventbus.Subscribe;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2020/4/24 10:55
 * @Version 1.0
 */
public class DataObserver1 {

    /**
     * 发布方法有且只能有一个参数
     */
    @Subscribe
    public void func(String msg) {
        System.out.println("DataObserver1 msg: " + msg);
    }
}
