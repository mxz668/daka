package com.daka.util.evenBus;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2020/4/24 11:04
 * @Version 1.0
 */
public class EvenBusTest {

    public static void main(String[] args){

        System.out.println(System.currentTimeMillis());

        DataObserver1 observer1 = new DataObserver1();
        DataObserver2 observer2 = new DataObserver2();

        EventBusCenter.register(observer1);
        EventBusCenter.register(observer2);

        System.out.println("============   start  ====================");

        // 只有注册的参数类型为String的方法会被调用
        EventBusCenter.post("post string method");
//        EventBusCenter.post(123);

        System.out.println("============ after unregister ============");
        // 注销observer2
        EventBusCenter.unregister(observer2);
        EventBusCenter.post("post string method");
//        EventBusCenter.post(123);

        System.out.println("============    end           =============");
    }
}
