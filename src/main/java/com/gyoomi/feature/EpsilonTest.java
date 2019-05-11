package com.gyoomi.feature;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以简单根据finalize是否执行来判断
 *
 * @author Leon
 * @version 2019/5/11 22:58
 */
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