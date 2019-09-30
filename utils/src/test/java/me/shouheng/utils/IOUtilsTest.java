package me.shouheng.utils;

import org.junit.Test;

import java.io.Closeable;

import me.shouheng.utils.store.IOUtils;

/**
 * 测试 {@link IOUtils}
 *
 * @author WngShhng 2019-05-08 20:04
 */
public class IOUtilsTest {

    /**
     * Test {@link IOUtils#safeCloseAll(Closeable...)} method
     */
    @Test
    public void testCloseAll() {
        IOUtils.safeCloseAll(null, null, null);
    }
}
