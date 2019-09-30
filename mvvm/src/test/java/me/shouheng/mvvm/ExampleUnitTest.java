package me.shouheng.mvvm;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void addd() {
        boolean result = Float.valueOf("3.2") >= 3.3F;
        System.out.println(result);
        Assert.assertTrue(result);
    }

    @Test
    public void test() {
        String data = "{\"time\":0,\"type\":0}";
        System.out.println(data.getBytes());
        Assert.assertEquals(Arrays.toString(data.getBytes()), "");
    }
}