package me.shouheng.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import me.shouheng.utils.data.StringFunction;
import me.shouheng.utils.data.StringUtils;

/**
 * 测试 {@link StringUtils}
 *
 * @author WngShhng 2019-05-08 20:04
 */
public class StringUtilsTest {

    /**
     * Test
     * 1. {@link StringUtils#bytes2HexString(byte[])} and
     * 2. {@link StringUtils#hexString2Bytes(String)} methods
     */
    @Test
    public void testBytes2HexString() {
        String oriStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String hexStr = StringUtils.bytes2HexString(oriStr.getBytes());
        System.out.println(hexStr);
        String bacStr = new String(StringUtils.hexString2Bytes(hexStr));
        System.out.println(bacStr);
        Assert.assertEquals(oriStr, bacStr);
    }

    /**
     * Test
     * 1. {@link StringUtils#base64String2Long(String)} and
     * 2. {@link StringUtils#long2Base64String(long)} methods.
     */
    @Test
    public void testLong2Base64String() {
        long oriLong = System.currentTimeMillis();
        System.out.println(oriLong);
        String base64 = StringUtils.long2Base64String(oriLong);
        System.out.println(base64);
        long bacLong = StringUtils.base64String2Long(base64);
        System.out.println(bacLong);
        Assert.assertEquals(oriLong, bacLong);
    }

    @Test
    public void testStringConnector() {
        List<Person> personList = Arrays.asList(
                new Person("A", 1),
                new Person("B", 2),
                new Person("C", 3),
                new Person("D", 4));
        String result = StringUtils.connect(personList, "@", new StringFunction<Person>() {
            @Override
            public String apply(Person person) {
                return person.name + "#" + person.age;
            }
        });
        System.out.println(result);
        Assert.assertEquals("A#1@B#2@C#3@D#4", result);
    }

    public static class Person {

        final String name;

        final int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
