package com.gyoomi.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * 新加一些实用的API
 *
 * @author Leon
 * @version 2019/5/1 22:24
 */
public class APITest {

    public static void main(String[] args) throws Exception {
        // test01();
        test02();
    }

    public static void test01() {
        List<String> list = List.of("aa", "bb", "cc");
        System.out.println(list);
        // 报错
        // java.lang.UnsupportedOperationException
        list.add("dd");
        System.out.println(list);

        // Set集合也是类似
    }

    public static void test02() {
        // of和copyOf
        List<String> list = List.of("aa", "bb", "cc");
        List<String> list2 = List.copyOf(list);
        System.out.println(list == list2);

        System.out.println("--------------------------");
        List<String> list3 = new ArrayList<>();
        List<String> list4 = List.copyOf(list3);
        System.out.println(list3 == list4);

        // 代码差不多，为什么一个为true,一个为false?
        // 来看下它们的源码
        // 发现
        // 可以看出 copyOf 方法会先判断来源集合是不是 AbstractImmutableList 类型的，
        // 如果是，就直接返回，如果不是，则调用 of 创建一个新的集合。

        // 示例2因为用的 new 创建的集合，不属于不可变 AbstractImmutableList 类的子类，
        // 所以 copyOf 方法又创建了一个新的实例，所以为false.

        // 注意：
        // 使用of和copyOf创建的集合为不可变集合，不能进行添加、删除、替换、排序等操作，不然会报 java.lang.UnsupportedOperationException 异常。
    }

    public static void test03() {

    }
}
