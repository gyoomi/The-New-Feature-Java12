package com.gyoomi.feature;

import java.util.List;
import java.util.stream.Stream;

/**
 * Stream 加强
 *
 * 流的处理
 *     1) 创建流
 *     2) 中间操作
 *     3) 终止操作
 *
 * @author Leon
 * @version 2019/5/1 23:04
 */
public class StreamTest {

    public static void main(String[] args) throws Exception {
        // test01();
        // test02();
        test03();
    }

    public static void test03() {
        // 流的迭代, 创建流
        Stream.iterate(1, t -> 2 * t).limit(10).forEach(System.out::println);

        System.out.println("------------------------------------");
        // 有限的迭代 小于Predicate：hasNext
        Stream.iterate(1, t -> t < 10000, t -> 2 * t).forEach(System.out::println);
    }

    public static void test02() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        // 从流中一直获取判定器为真的元素, 一旦遇到元素为假, 就终止处理
        list.stream().takeWhile(x -> x < 3).forEach(System.out::println);
        System.out.println("------------------------------------------");
        // 从流中一直获取判定器为假的元素, 遇到元素为真, 就忽略跳过
        list.stream().dropWhile(x -> x < 3).forEach(System.out::println);
    }

    public static void test01() {
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
    }


}
