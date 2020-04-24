package com.daka.util.evenBus;

import com.google.common.eventbus.Subscribe;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2020/4/24 10:55
 * @Version 1.0
 */
public class DataObserver2 {

    @Subscribe
    public void func(String msg) {
        System.out.println("DataObserver2 m1 msg: " + msg);
    }

    @Subscribe
    public void func2(Integer msg) {
        System.out.println("DataObserver2 m2 msg: " + msg);
    }
}
