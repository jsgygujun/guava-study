package com.jsgygujun.code.event_bus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import java.util.concurrent.Executors;

/**
 * @author gujun@qiyi.com
 * @since 2020/6/17 4:03 下午
 */
public class ExpAsyncEventBus {
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
        // 订阅方法，必须使用@Subscribe注解声明，且只能有一个参数，当有消息发布的时候，该方法自动被调用。
        // 使用@AllowConcurrentEvents注解说明采用异步方式执行onMessage方法。
        @Subscribe
        @AllowConcurrentEvents
        public void onMessage(ExpEventBus.Event event) {
            System.out.println("Thread:" + Thread.currentThread().getName() + ", name: " + name  + " received a message: " + event.getMessage());
        }
    }

    public static void main(String[] args) {
        // 定义异步EventBus对象
        final AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
        // 向EventBus对象注册监听对象
        asyncEventBus.register(new ExpEventBus.EventListener("listener1"));
        asyncEventBus.register(new ExpEventBus.EventListener("listener2"));
        // 发布一个事件，该事件会通知到所有注册的监听者
        asyncEventBus.post(new ExpEventBus.Event("Hello every listener, this is a test message!"));
    }
}
