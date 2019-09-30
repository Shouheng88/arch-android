package me.shouheng.utils.data;

import android.support.annotation.ArrayRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;

import java.util.List;

import me.shouheng.utils.UtilsApp;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/5/7 22:38
 */
public final class StringUtils {

    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final char[] BASE_64_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '+', '/', };

    /*----------------------------------normal strings--------------------------------------*/

    /**
     * 判断指定字符是否为空白字符，空白符包含：空格、tab 键、换行符
     *
     * @param s 要判断的字符串
     * @return 当字符串为空或者字符串中所有的字符都是空白字符的时候返回 true
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 指定的字符串是否为空，null 或者长度为 0
     *
     * @param s 字符串
     * @return true 表示为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 指定的字符串 {@link String#trim()} 之后是否为空
     *
     * @param s 字符串
     * @return true 表示为空
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两个 CharSequence 是否相等
     *
     * @param s1 CharSequence 1
     * @param s2 CharSequence 2
     * @return true 表示相等
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 忽略大小写之后，判断两个 String 是否相等
     *
     * @param s1 String 1
     * @param s2 String 2
     * @return true 表示相等
     */
    public static boolean equalsIgnoreCase(final String s1, final String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 获取 CharSequence 的长度
     *
     * @param s CharSequence
     * @return null 的话返回 0，否则返回字符串长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 字符串的首字符大写
     *
     * @param s 字符串
     * @return 处理之后的字符串
     */
    public static String upperFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 字符串的首字符小写
     *
     * @param s 字符串
     * @return 处理之后的字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (s == null || s.length() == 0) return "";
        if (!Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 字符串反转
     *
     * @param s 字符串
     * @return 反转之后的字符串
     */
    public static String reverse(final String s) {
        if (s == null) return "";
        int len = s.length();
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 获取指定的字节数组对应的十六进制字符串，按照 ASCII 码表计算
     * 比如 ABCDEFGHIJKLMNOPQRSTUVWXYZ
     * 将得到 4142434445464748494A4B4C4D4E4F505152535455565758595A
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            // 字节的高八位
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            // 字节的低八位
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 将十六进制字符串转换回字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexString2Bytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * 将数字转换成六十四进制字符串
     *
     * @param number 数字
     * @return 字符串
     */
    public static String long2Base64String(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1L; // 截取后几位，在 [0,63] 之间
        do {
            buf[--charPos] = BASE_64_DIGITS[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    /**
     * 将六十四进制字符串还原回数字
     *
     * @param base64String 六十四进制字符串
     * @return 数字
     */
    public static long base64String2Long(String base64String) {
        long result = 0;
        int length = base64String.length();
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 0; j < BASE_64_DIGITS.length; j++) {
                if (base64String.charAt(i) == BASE_64_DIGITS[j]) {
                    result += ((long) j) << 6 * (base64String.length() - 1 - i);
                }
            }
        }
        return result;
    }

    public static <T> String connect(List<T> list, String connector) {
        return connect(list, connector, new StringFunction<T>() {
            @Override
            public String apply(T from) {
                return from.toString();
            }
        });
    }

    /**
     * 将传入的列表按照指定的格式拼接起来
     *
     * @param list      列表
     * @param connector 连接的字符串
     * @param function  对象到字符串映射格式
     * @param <T>       对象类型
     * @return          拼接结果
     */
    public static <T> String connect(List<T> list, String connector, StringFunction<T> function) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (int i=0, len=list.size(); i<len; i++) {
            if (i != len-1) {
                sb.append(function.apply(list.get(i))).append(connector);
            } else {
                sb.append(function.apply(list.get(i)));
            }
        }
        return sb.toString();
    }

    /*----------------------------------android resources--------------------------------------*/

    public static String getString(@StringRes int id) {
        return UtilsApp.getApp().getResources().getString(id);
    }

    public static String getString(@StringRes int id, Object... formatArgs) {
        return UtilsApp.getApp().getResources().getString(id, formatArgs);
    }

    public static CharSequence getText(@StringRes int id) {
        return UtilsApp.getApp().getResources().getText(id);
    }

    public static CharSequence getQuantityText(@PluralsRes int id, int quantity) {
        return UtilsApp.getApp().getResources().getQuantityText(id, quantity);
    }

    public static String getQuantityString(@PluralsRes int id, int quantity) {
        return UtilsApp.getApp().getResources().getQuantityString(id, quantity);
    }

    public static String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs) {
        return UtilsApp.getApp().getResources().getQuantityString(id, quantity, formatArgs);
    }

    public static CharSequence[] getTextArray(@ArrayRes int id) {
        return UtilsApp.getApp().getResources().getTextArray(id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return UtilsApp.getApp().getResources().getStringArray(id);
    }

    public static String format(@StringRes int resId, Object... arg) {
        try {
            return String.format(UtilsApp.getApp().getString(resId), arg);
        } catch (Exception e) {
            return UtilsApp.getApp().getString(resId);
        }
    }

    /*----------------------------------inner methods--------------------------------------*/

    private StringUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
