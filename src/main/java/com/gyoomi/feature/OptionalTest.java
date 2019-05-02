package com.gyoomi.feature;

import org.junit.Test;

import java.util.Optional;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/5/2 23:37
 */
public class OptionalTest {

    @Test
    public void test01() {
        // of方法中如果传入的参数是null, 会抛出空指针异常
        // ofNullable可以兼容空指针, 但是实际传入null后要小心
        // Optional<Object> o = Optional.of(null);
        Optional<Object> o = Optional.ofNullable(null);
        // o.orElse(new Object());
    }
}
