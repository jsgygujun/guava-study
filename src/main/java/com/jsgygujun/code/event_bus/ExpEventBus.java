package com.jsgygujun.code.event_bus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * @author gujun@qiyi.com
 * @since 2020/6/16 11:26 上午
 */
public class ExpEventBus {
    // 自定义事件
    public static class Event {
        private String message;
        public Event(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
    }

    // 自定义订阅者（监听者）
    public static class EventListener {
        private String name;
        public EventListener(String name) {
            this.name = name;
        }
        // 订阅方法，必须使用注解声明，且只能有一个参数，当有消息发布的时候，该方法自动被调用。
        @Subscribe
        public void onMessage(Event event) {
            System.out.println(name + " received a message: " + event.getMessage());
        }
    }

    public static void main(String[] args) {
        // 定义EventBus对象
        final EventBus eventBus = new EventBus("EventBus");
        // 向EventBus对象注册监听对象
        eventBus.register(new EventListener("listener1"));
        eventBus.register(new EventListener("listener2"));
        // 发布一个事件，该事件会通知到所有注册的监听者
        eventBus.post(new Event("Hello every listener, this is a test message!"));
    }
}
