package com.daka.util.evenBus;

import com.google.common.eventbus.EventBus;

/**
 * 描述：
 *
 * @Author: mabiao
 * @Date: 2020/4/24 11:04
 * @Version 1.0
 */
public class EventBusCenter {
    private static EventBus eventBus = new EventBus();

    private EventBusCenter() {

    }

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object obj) {
        eventBus.register(obj);
    }

    public static void unregister(Object obj) {
        eventBus.unregister(obj);
    }

    public static void post(Object obj) {
        eventBus.post(obj);
    }
}
