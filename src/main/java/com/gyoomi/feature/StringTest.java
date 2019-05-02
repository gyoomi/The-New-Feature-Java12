package com.gyoomi.feature;

import org.junit.Test;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/5/2 23:18
 */
public class StringTest {

    @Test
    public void test01() {
        // 判断字符串中的字符是否都是空白
        // 可以看出 空格，换行都是空白
        String string = " \t  \r\n ";
        System.out.println(string.isBlank());
    }

    @Test
    public void test02() {
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
    }

    @Test
    public void test03() {
        // 去重字符串首部的空白
        String string = " \t  \r\nabc ";
        System.out.println(string.stripLeading());
        System.out.println(string.stripLeading().length());

        System.out.println("----------------------------");

        // 去重字符串尾部的空白
        String string2 = " \t  \r\nabc ";
        System.out.println(string2.stripTrailing());
        System.out.println(string2.stripTrailing().length());
    }
}
