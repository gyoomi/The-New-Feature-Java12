package com.gyoomi.feature;

import java.util.function.Consumer;

/**
 * 局部变量类型推断（var关键字）
 *
 * @author Leon
 * @version 2019/5/1 22:09
 */
public class VarTest {

    public static void main(String[] args) {
        var str = "hello java12";
        System.out.println(str);
        System.out.println(str.getClass());
        System.out.println("-------------------------------------");
        test01();
    }

    public static void test01() {
        Consumer<String> consumer = (var x) -> System.out.println(x.toUpperCase());
        consumer.accept("sssssss");
        System.out.println("********************************");
        Consumer<String> consumer2 = (@Deprecated var x) -> System.out.println(x.toUpperCase());
        consumer2.accept("sssssss");
    }
}
