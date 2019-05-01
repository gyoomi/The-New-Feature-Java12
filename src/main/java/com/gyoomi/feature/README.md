## Java12 新特性
### 一、JShell
1. JDK9加入
2. 说明
>java9引入了jshell这个交互性工具，让Java也可以像脚本语言一样来运行，可以从控制台启动 jshell ，在 jshell 
>中直接输入表达式并查看其执行结果。当需要测试一个方法的运行效果，或是快速的对表达式进行求值时，
>jshell 都非常实用.

>除了表达式之外，还可以创建 Java 类和方法。jshell 也有基本的代码完成功能。我们在教人们如何编写 Java 的过程中，
>不再需要解释 “public static void main（String [] args）” 这句废话。
3. 使用(略)
### 二、Dynamic Class-File Constants类文件新添的一种结构
1. 说明
>java的类型文件格式将被拓展，支持一种新的常量池格式：CONSTANT_Dynamic，加载CONSTANT_Dynamic会将创建委托给bootstrap方法。
2. 目标
>其目标是降低开发新形式的可实现类文件约束带来的成本和干扰。
### 三、局部变量类型推断（var关键字）
1. 什么是局部变量类型推断
``` 
        var str = "hello java12";
        System.out.println(str);
        System.out.println(str.getClass());
```

大家看出来了，局部变量类型推断就是左边的类型直接使用 var 定义，而不用写具体的类型，
编译器能根据右边的表达式自动推断类型，如上面的String。

2. 优点

在声明隐式类型的lambda表达式的形参时允许使用var
使用var的好处是在使用lambda表达式时给参数加上注解
(@Deprecated var x, @Nullable var y) -> x.process(y);

### 四、新加一些实用的API
#### 1. 新的本机不可修改集合API
1.1 新的本机不可修改集合API
``` 
        List<String> list = List.of("aa", "bb", "cc");
        System.out.println(list);
        // 报错
        // java.lang.UnsupportedOperationException
        list.add("dd");
        System.out.println(list);
```

1.2 List.of()和List.copyOf()

通过源码阅读发现：

>看出 copyOf 方法会先判断来源集合是不是 AbstractImmutableList 类型的，如果是，就直接返回，如果不是，则调用 of 创建一个新的集合。

>示例2因为用的 new 创建的集合，不属于不可变 AbstractImmutableList 类的子类，所以 copyOf 方法又创建了一个新的实例，所以为false.

1.3 注意

使用of和copyOf创建的集合为不可变集合，不能进行添加、删除、替换、排序等操作，不然会报 java.lang.UnsupportedOperationException 异常。

#### 2. Stream 加强
2.1 增加单个参数构造方法，可为null
``` 
        Stream<Object> of = Stream.of();
        of.forEach(System.out::println);
        // System.out.println("---------------------------------");
        // Stream<Object> of2 = Stream.of(null);
        // of2.forEach(System.out::println);
        // 报错源码：null.length ===> NPE
        //    public static <T> Stream<T> stream(T[] array) {
        //        return stream(array, 0, array.length);
        //    }

        System.out.println("**************************************");
        Stream<Object> of2 = Stream.ofNullable(null);
        of2.forEach(System.out::println);
        // 可以看出并没报错
```
2.2 takeWhile 和 dropWhile

2.3 iterate重载

#### 3. 新的字符串处理方法






















### 一、JShell



































