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

- __...在传统的Java事件中__：将您的对象传递给每个生产者的`registerCustomerChangeEventListener`方法。这些方法很少在公共接口中定义，因此，除了了解每个可能的生产者之外，还必须了解其类型。
- __...使用EventBus__：将您的对象传递给`EventBus`上的`EventBus.register(Object)`方法。您需要确保您的对象与事件生产者共享一个`EventBus`实例。

要侦听常见的事件超类型（例如`EventObject`或`Object`）...

- __...在传统的Java事件中__：不容易
- __...使用EventBus__：事件会自动分派给任何超类型的监听器，从而允许接口类型的侦监听器或`Object`的“通配符侦听器”。

要侦听和检测没有侦听器调度的事件...

- __...在传统的Java事件中__：将代码添加到每种事件调度方法中（可能使用AOP）。
- __...使用EventBus__：订阅`DeadEvent`。 `EventBus`将通知您任何已发布但尚未交付的事件。 （方便调试。）

### 对于生产者

跟踪事件的监听者...

- __...在传统的Java事件中__：编写代码来管理对象的侦听器列表，包括同步，或使用诸如EventListenerList之类的实用程序类。
- __...使用EventBus__：EventBus为您做到这一点。

要将事件调度给监听者...

- __...在传统的Java事件中__：编写一种将事件调度到每个事件侦听器的方法，包括错误隔离和（如果需要）异步性。
- __...使用EventBus__：将事件对象传递给`EventBus`的`EventBus.post(Object)`方法。

## 术语

`EventBus`系统和代码使用以下术语来讨论事件分发：

| 术语             | 说明                                                         |
| :--------------- | ------------------------------------------------------------ |
| Event            | 可能发布到总线的任何对象                                     |
| Subscribing      | 向EventBus注册侦听器的行为，以便其处理程序方法将接收事件。   |
| Listener         | 希望通过公开处理程序方法来接收事件的对象。                   |
| Handler method   | EventBus用于传递已发布事件的公共方法。处理程序方法由@Subscribe批注标记。 |
| Posting an event | 通过EventBus将事件提供给所有侦听器。                         |

## FAQ

__为什么我必须创建自己的`EventBus`，而不是使用单例？__

`EventBus`没有指定使用方式；您可以为应用程序的每个组件使用单独的`EventBus`实例，或者使用单独的实例按上下文或主题来分隔事件。这也使得在测试中创建和销毁`EventBus`对象变得很简单。当然，如果您也可以在程序的整个生命周期内使用`EventBus`单例。只需让您的容器（如Guice）在全局范围内将EventBus创建为一个单例（或将其存储在静态字段中，如果您喜欢的话）。简而言之，`EventBus`不是单例，因为我们不想为您做出该决定。随心所欲地使用它。

__我可以从事件总线上取消注册侦听器吗？__

可以，使用`EventBus.unregister`，但是我们发现只有很少需要它：

- 大多数监听者是在启动或延迟初始化时注册的，并在应用程序的生命周期内保持不变。
- 范围特定的`EventBus`实例可以处理临时事件分发（例如，在请求范围内的对象之间分发事件）
- 为了进行测试，可以轻松创建和销毁`EventBus`实例，从而无需显式注销。

__为什么使用批注标记处理程序方法，而不是要求侦听器实现接口？__

我们认为，事件总线`@Subscribe`注释可以像实现接口一样显式传达您的意图（或者可能更多），同时让您可以随意将事件处理程序方法放置在所需的位置，并为它们提供意图显示的名称。

传统的Java事件使用侦听器接口，该接口通常仅使用几种方法-通常是一种。这有许多缺点：

- 任何一个类只能对给定事件实施单个响应。 
- 监听器接口方法可能会冲突。
- 该方法必须以事件（例如`handleChangeEvent`）命名，而不是其用途（例如`recordChangeInJournal`）命名。
- 每个事件通常都有其自己的接口，而没有用于一系列事件（例如，所有UI事件）的公共父接口。





