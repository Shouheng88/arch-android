package me.shouheng.utils;

import org.junit.Test;

import me.shouheng.utils.data.EncryptUtils;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/5/12 13:27
 */
public class EncryptUtilsTest {

    @Test
    public void testMd2() {
        String src = "SRC";
        System.out.println(EncryptUtils.md2(src));
    }
}
