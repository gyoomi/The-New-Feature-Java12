package com.gyoomi.feature;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * InputStream 加强
 *
 * @author Leon
 * @version 2019/5/2 23:39
 */
public class InputStreamTest {

    @Test
    public void test01() throws Exception {
        ClassLoader classLoader = InputStreamTest.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("1.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        inputStream.transferTo(fileOutputStream);
        inputStream.close();
        fileOutputStream.close();
    }
}
