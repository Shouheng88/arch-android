package me.shouheng.utils.data;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/5/12 15:50
 */
public final class EncodeUtils {

    /*---------------------------------- URL --------------------------------------*/

    public static String urlEncode(final String input) {
        return urlEncode(input, "UTF-8");
    }

    public static String urlEncode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLEncoder.encode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    public static String urlDecode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            return URLDecoder.decode(input, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    /*---------------------------------- Base64 --------------------------------------*/

    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    public static byte[] base64Encode(final byte[] input) {
        if (input == null || input.length == 0) return new byte[0];
        return Base64.encode(input, Base64.NO_WRAP);
    }

    public static String base64Encode2String(final byte[] input) {
        if (input == null || input.length == 0) return "";
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    public static byte[] base64Decode(final String input) {
        if (input == null || input.length() == 0) return new byte[0];
        return Base64.decode(input, Base64.NO_WRAP);
    }

    public static byte[] base64Decode(final byte[] input) {
        if (input == null || input.length == 0) return new byte[0];
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /*---------------------------------- 内部方法 --------------------------------------*/

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
