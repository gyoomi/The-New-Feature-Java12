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
``` 
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        // 从流中一直获取判定器为真的元素, 一旦遇到元素为假, 就终止处理
        list.stream().takeWhile(x -> x < 3).forEach(System.out::println);
        System.out.println("------------------------------------------");
        // 从流中一直获取判定器为假的元素, 遇到元素为真, 就忽略跳过
        list.stream().dropWhile(x -> x < 3).forEach(System.out::println);
```
2.3 iterate重载

``` 
        // 流的迭代, 创建流
        Stream.iterate(1, t -> 2 * t).limit(10).forEach(System.out::println);

        System.out.println("------------------------------------");
        // 有限的迭代 小于Predicate：hasNext
        Stream.iterate(1, t -> t < 10000, t -> 2 * t).forEach(System.out::println);
```

#### 3. 新的字符串处理方法

3.1 判断字符串中的字符是否都是空白

``` 
        // 判断字符串中的字符是否都是空白
        // 可以看出 空格，换行都是空白
        String string = " \t  \r\n ";
        System.out.println(string.isBlank());
```

3.2 strip去重字符串首尾的空白

``` 
        // strip去重字符串首尾的空白, 包括英文和其他所有语言中的空白字符
        var string = " \t  \r\n abc \t ";
        System.out.println(string.strip());
        System.out.println(string.strip().length());
        // abc
        // 3

        System.out.println("-----------------------------");
        // trim去重字符串首尾的空白字符, 只能去除码值小于等于32的空白字符
        var string2 = " \t  \r\n abc \t　　";
        System.out.println(string2.trim());
        System.out.println(string2.trim().length());
```

3.3 去重字符串首部的空白

``` 
        // 去重字符串首部的空白
        String string = " \t  \r\nabc ";
        System.out.println(string.stripLeading());
        System.out.println(string.stripLeading().length());

        System.out.println("----------------------------");

        // 去重字符串尾部的空白
        String string2 = " \t  \r\nabc ";
        System.out.println(string2.stripTrailing());
        System.out.println(string2.stripTrailing().length());
```

#### 4. Optional 加强

``` 
        // of方法中如果传入的参数是null, 会抛出空指针异常
        // ofNullable可以兼容空指针, 但是实际传入null后要小心
        // Optional<Object> o = Optional.of(null);
        Optional<Object> o = Optional.ofNullable(null);
        // o.orElse(new Object());
```

#### 5. InputStream 加强
5.1 transferTo

可以用来将数据直接传输到OutputStream，
这是在处理原始数据流时非常常见的一种用法，如下示例。

``` 
    public void test01() throws Exception {
        InputStream inputStream = InputStreamTest.class.getResourceAsStream("/1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        inputStream.transferTo(fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
    }
```

#### 6. 移除过时或无用类
6.1 移除项
- 移除了com.sun.awt.AWTUtilities
- 移除了sun.misc.Unsafe.defineClass，
- 使用java.lang.invoke.MethodHandles.Lookup.defineClass来替代
- 移除了Thread.destroy()以及 Thread.stop(Throwable)方法
- 移除了sun.nio.ch.disableSystemWideOverlappingFileLockCheck、sun.locale.formatasdefault属性
- 移除了jdk.snmp模块
- 移除了javafx，openjdk估计是从java10版本就移除了，oracle jdk10还尚未移除javafx，而java11版本则oracle的jdk版本也移除了javafx
- 移除了Java Mission Control，从JDK中移除之后，需要自己单独下载
- 移除了这些Root Certificates ：Baltimore Cybertrust Code Signing CA，SECOM ，AOL and Swisscom

6.2 废弃项
- -XX+AggressiveOpts选项
- -XX:+UnlockCommercialFeatures
- -XX:+LogCommercialFeatures选项也不再需要

### 五、标准Java异步HTTP客户端
这是 Java 9 开始引入的一个处理 HTTP 请求的的 HTTP Client API，
该 API 支持同步和异步，而在 Java 11 中已经为正式可用状态，你可以在java.net
包中找到这个API。

``` 
    // 同步用法
    public void testGet() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = httpClient.send(request, bodyHandler);
        String result = response.body();
        System.out.println(result);
    }
    
    // 异步用法
    public void testGetAsyn() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://www.baidu.com")).build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, bodyHandler);
        response.thenApply(HttpResponse::body).thenAccept(System.out::println);
        Thread.sleep(1000);
    }
```

看来以后不需要Apache的HttpClient的工具包了。

### 六、简化的编译运行程序
6.1 JEP330 增强java启动器支持运行单个java源代码文件的程序  
    
1. 注意点
   1. 执行源文件中的第一个类, 第一个类必须包含主方法
   2. 并且不可以使用别源文件中的自定义类, 本文件中的自定义类是可以使用的.
6.2 运行差异
   
2. 之前的做法
   ```
   // 编译
   javac Javastack.java
   
   // 运行
   java Javastack
   ```
   
3. 简化后的用法
   
   ``` 
   java Javastack.java
   ```
   
   >在我们的认知里面，要运行一个 Java 源代码必须先编译，再运行，
   >两步执行动作。而在Java 11 版本中，通过一个 java 命令就直接搞定了，如上所示。
   
### 七、Unicode 10
Unicode 10 增加了8518个字符, 总计达到了136690个字符. 并且增加了4个脚本.
同时还有56个新的emoji表情符号.

### 八、Epsilon垃圾收集器
A NoOp Garbage Collector

JDK上对这个特性的描述是: 开发一个处理内存分配但不实现任何实际内存回收机制的GC, 一旦可用堆内存用完, 
JVM就会退出

如果有System.gc()调用, 实际上什么也不会发生(这种场景下和-XX:+DisableExplicitGC效果一样), 因为没有内存回收, 这个实现可能会警告用户尝试强制GC是徒劳.

用法 :` -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC`

用途：
- 性能测试(它可以帮助过滤掉GC引起的性能假象)
- 内存压力测试(例如,知道测试用例 应该分配不超过1GB的内存, 我们可以使用-Xmx1g –XX:+UseEpsilonGC, 如果程序有问题, 则程序会崩溃)
- 非常短的JOB任务(对象这种任务, 接受GC清理堆那都是浪费空间)
- VM接口测试
- Last-drop 延迟&吞吐改进

测试代码：

``` 
public class EpsilonTest {

    /**
     * -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        List<Garbage> list = new ArrayList<>();
        boolean flag = true;
        int count = 0;
        while (flag) {
            list.add(new Garbage());
            if (count++ == 500) {
                list.clear();
            }
        }
    }
}

class Garbage {

    private double d1 = 1;
    private double d2 = 2;

    /**
     * 这个方法是GC在清除本对象时, 会调用一次
     */
    @Override
    public void finalize() {
        System.out.println(this + " collecting");
    }
}
```
### 九、ZGC垃圾收集器
ZGC, A Scalable Low-Latency Garbage Collector(Experimental)
ZGC, 这应该是JDK11最为瞩目的特性, 没有之一. 但是后面带了Experimental, 说明这还不建议用到生产环境.

- GC暂停时间不会超过10ms,在实验的过程基本不会超过2ms
- 既能处理几百兆的小堆, 也能处理几个T的大堆(OMG)
- 和G1相比, 应用吞吐能力不会下降超过15%
- 为未来的GC功能和利用colord指针以及Load barriers优化奠定基础
- 初始只支持Linux下64位系统

>心里默念一句：真屌

ZGC的设计目标是：支持TB级内存容量，暂停时间低（<10ms），
对整个程序吞吐量的影响小于15%。 将来还可以扩展实现机制，以支持不少令人兴奋的功能，
例如多层堆（即热对象置于DRAM和冷对象置于NVMe闪存），或压缩堆。

GC是java主要优势之一. 然而, 当GC停顿太长, 就会开始影响应用的响应时间.
消除或者减少GC停顿时长, java将对更广泛的应用场景是一个更有吸引力的平台. 
此外, 现代系统中可用内存不断增长,用户和程序员希望JVM能够以高效的方式充分利用
这些内存, 并且无需长时间的GC暂停时间.

ZGC是一个并发, 基于region, 压缩型的垃圾收集器, 只有root扫描阶段会STW, 
因此GC停顿时间不会随着堆的增长和存活对象的增长而变长.

实验数据对比

``` 
ZGC : avg 1.091ms	max:1.681
G1	: avg 156.806  max:543.846
```

用法: `-XX:+UnlockExperimentalVMOptions –XX:+UseZGC`,因为ZGC还处于实验阶段, 所以需要通过JVM参数来解锁这个特性


### 十、Java Flight Recorder
Flight Recorder源自飞机的黑盒子。

Flight Recorder以前是商业版的特性，在java11当中开源出来，它可以导出事件到文件中，之后可以用Java Mission Control来分析。
可以在应用启动时配置java -XX:StartFlightRecording，或者在应用启动之后，使用jcmd来录制，比如

$ jcmd <pid> JFR.start
$ jcmd <pid> JFR.dump filename=recording.jfr
$ jcmd <pid> JFR.stop

是 Oracle 刚刚开源的强大特性。我们知道在生产系统进行不同角度的 Profiling，
有各种工具、框架，但是能力范围、可靠性、开销等，大都差强人意，要么能力不全面，
要么开销太大，甚至不可靠可能导致 Java 应用进程宕机。

而 JFR 是一套集成进入 JDK、JVM 内部的事件机制框架，通过良好架构和设计的框架，
硬件层面的极致优化，生产环境的广泛验证，它可以做到极致的可靠和低开销。
在 SPECjbb2015 等基准测试中，JFR 的性能开销最大不超过 1%，所以，
工程师可以基本没有心理负担地在大规模分布式的生产系统使用，这意味着，
我们既可以随时主动开启 JFR 进行特定诊断，也可以让系统长期运行 JFR，
用以在复杂环境中进行“After-the-fact”分析。还需要苦恼重现随机问题吗？
JFR 让问题简化了很多。

在保证低开销的基础上，JFR 提供的能力也令人眼前一亮，例如：我们无需 BCI 
就可以进行 Object Allocation Profiling，终于不用担心 BTrace 之类把进程搞挂了。
对锁竞争、阻塞、延迟，JVM GC、SafePoint 等领域，进行非常细粒度分析。
甚至深入 JIT Compiler 内部，全面把握热点方法、内联、逆优化等等。
JFR 提供了标准的 Java、C++ 等扩展 API，可以与各种层面的应用进行定制、集成，
为复杂的企业应用栈或者复杂的分布式应用，提供 All-in-One 解决方案。
而这一切都是内建在 JDK 和 JVM 内部的，并不需要额外的依赖，开箱即用。
### 十一、 Switch 表达式（预览功能）
``` 
    // 传统的使用方式
    @Test
    public void testOldSwitch() {
        int count = new Random(100).nextInt();
        switch (count) {
            case 60:
                System.out.println("及格");
                break;
            case 80:
                System.out.println("优良");
                break;
            case 90:
                System.out.println("优秀");
                break;
            default:
                System.out.println("nothing");
                break;
        }
    }

    // 都知道，switch 语句如果漏写一个 break，那么逻辑往往就跑偏了，
    // 这种方式既繁琐，又容易出错
    // 下面这种方式就避免了这种问题.
    @Test
    public void testNewSwitch() {
        int count = new Random().nextInt(10);
        System.out.println(count);
        switch (count) {
            case 1,2,3,4,5 -> System.out.println("不及格");
            case 6,7,8 -> System.out.println("优良");
            case 9,10 -> System.out.println("优秀");
            default -> System.out.println("没有合适的");
        }
    }

    // 优雅地表达特定场合计算逻辑
    @Test
    public void testNewSwitch02() {
        int count = new Random().nextInt(10);
        System.out.println(count);
        int result = switch (count) {
            case 1,2,3,4,5 -> 0;
            case 6,7,8 -> 2;
            case 9,10 -> 5;
            default -> 0;
        };
        System.out.println("加分为：" + result);
    }
```

### 十二、完全支持Linux容器（包括Docker）
许多运行在Java虚拟机中的应用程序（包括Apache Spark和Kafka等数据服务以及传统的
企业应用程序）都可以在Docker容器中运行。但是在Docker容器中运行Java应用程序一直
存在一个问题，那就是在容器中运行JVM程序在设置内存大小和CPU使用率后，
会导致应用程序的性能下降。这是因为Java应用程序没有意识到它正在容器中运行。
随着Java 10的发布，这个问题总算得以解决，JVM现在可以识别由容器控制组（cgroups）
设置的约束。可以在容器中使用内存和CPU约束来直接管理Java应用程序。
































