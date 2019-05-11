package com.gyoomi.feature;

import org.junit.Test;

import java.util.Random;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/5/11 23:06
 */
public class SwitchTest {

    @Test
    public void testOldSwitch() {
        int count = new Random().nextInt(10);
        switch (count) {
            case 1,2,3,4,5:
                System.out.println("及格");
                //break;
            case 6,7,8:
                System.out.println("优良");
                //break;
            case 9,10:
                System.out.println("优秀");
                //break;
            default:
                System.out.println("nothing");
                break;
        }
    }

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
}
