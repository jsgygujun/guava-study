# Event Bus

__EventBus__允许组件之间进行`发布-订阅`式通信，而无需组件之间显式注册（因此彼此了解）。它专门设计为代替传统使用显式注册的Java进程内事件分发。它不是通用的`发布-订阅`系统，也不是用于进程间通信的。

## 示例

```java
// 该类工程由容器注册
class EventBusChangeRecorder {
  @Subscribe
  public void recordCustomerChange(ChangeEvent e) {
    recordChange(e.getChange());
  }
}
// 在初始化的某个地方
eventBus.register(new EventBusChangeRecorder());
// 发布消息
public void changeCustomer() {
  ChangeEvent event = getChangeEvent();
  eventBus.post(event);
}
```

## 一分钟指南

转换现有基于`EventListener`的系统以使用`EventBus`是很容易的。

### 对于监听者

监听特定类型的事件（例如，`CustomerChangeEvent`）...

- __...在传统的Java事件中__： 实现用事件定义的接口，例如`CustomerChangeEventListener`
- __...使用EventBus__：创建一个接受`CustomerChangeEvent`作为其唯一参数的方法，并使用`@Subscribe`批注对其进行标记。

向事件生产者注册侦听的方法...

- __...在传统的Java事件中__：
- __...使用EventBus__：

### 对于生产者



## 术语

`EventBus`系统和代码使用以下术语来讨论事件分发：

| 术语             | 说明                                                         |
| :--------------- | ------------------------------------------------------------ |
| Event            | 可能发布到总线的任何对象                                     |
| Subscribing      | 向EventBus注册侦听器的行为，以便其处理程序方法将接收事件。   |
| Listener         | 希望通过公开处理程序方法来接收事件的对象。                   |
| Handler method   | EventBus用于传递已发布事件的公共方法。处理程序方法由@Subscribe批注标记。 |
| Posting an event | 通过EventBus将事件提供给所有侦听器。                         |

