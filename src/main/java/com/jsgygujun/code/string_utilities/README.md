# String 辅助类

## Joiner 类

用分隔符将一串字符串连接在一起可能会很棘手，但事实并非如此。如果您的序列包含空值，则可能会更加困难。 Joiner的流利风格使其变得简单。

```java
Joiner joiner = Joiner.on("; ").skipNulls();
return joiner.join("Harry", null, "Ron", "Hermione");
```

返回字符串"Harry; Ron; Hermione"。或者，可以使用`useForNull(String)`指定字符串代替`null`，而不是使用`skipNulls`。

您也可以在对象上使用`Joiner`，这些对象将使用其`toString()`进行转换，然后再进行连接。

```java
Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"
```

__警告__：`Joiner`实例始终是不可变的。`Joiner`配置方法将始终返回一个新的`Joiner`对象，您必须使用新的对象才能获得所需的语义。这使任何`Joiner`线程都安全，并可用作`static final`常量。

Joiner类不仅返回字符串，而且具有可以与StringBuilder类一起使用的方法:

```java
StringBuilder sb = new StringBuilder("sb: ");
Joiner joiner = Joiner.on("|").skipNulls();
joiner.appendTo(sb, "foo", "bar", null, "baz"); // sb: foo|bar|baz
```

如我们所见，`Joiner`是一个非常有用的类，它使一个常见任务非常容易处理。在继续之前，有一个特殊的方法需要介绍-`MapJoiner`方法。 `MapJoiner`方法的工作方式与`Joiner`类相同，但它使用给定的分隔符将给定的字符串作为键值对连接在一起。 MapJoiner使用方法如下：

```java
Joiner.MapJoiner mapJoiner = Joiner.on("#").withKeyValueSeparator("=");
Map<String, String> map = new HashMap<>();
map.put("One", "1");
map.put("two", "2");
map.put("three", "3");
String result = mapJoiner.join(map); // One=1#two=2#three=3
```

