package me.shouheng.utils.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static me.shouheng.utils.data.StringUtils.bytes2HexString;
import static me.shouheng.utils.data.StringUtils.isSpace;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/5/7 23:21
 */
public final class EncryptUtils {

    /*---------------------------------- MD2 --------------------------------------*/

    public static String md2(final String data) {
        if (data == null || data.length() == 0) return "";
        return md2(data.getBytes());
    }

    public static String md2(final byte[] data) {
        return bytes2HexString(md2ToBytes(data));
    }

    public static byte[] md2ToBytes(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /*---------------------------------- MD5 --------------------------------------*/

    public static String md5(final String data) {
        if (data == null || data.length() == 0) return "";
        return md5(data.getBytes());
    }

    public static String md5(final String data, final String salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return bytes2HexString(md5ToBytes(data.getBytes()));
        if (data == null) return bytes2HexString(md5ToBytes(salt.getBytes()));
        return bytes2HexString(md5ToBytes((data + salt).getBytes()));
    }

    public static String md5(final byte[] data) {
        return bytes2HexString(md5ToBytes(data));
    }

    public static String md5(final byte[] data, final byte[] salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return bytes2HexString(md5ToBytes(data));
        if (data == null) return bytes2HexString(md5ToBytes(salt));
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(md5ToBytes(dataSalt));
    }

    public static byte[] md5ToBytes(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    public static String md5File(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return md5File(file);
    }

    public static byte[] md5FileToBytes(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return md5FileToBytes(file);
    }

    public static String md5File(final File file) {
        return bytes2HexString(md5FileToBytes(file));
    }

    public static byte[] md5FileToBytes(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*---------------------------------- SHA1 --------------------------------------*/

    public static String sha1(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha1(data.getBytes());
    }

    public static String sha1(final byte[] data) {
        return bytes2HexString(sha1ToBytes(data));
    }

    public static byte[] sha1ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /*---------------------------------- SHA224 --------------------------------------*/

    public static String sha224(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha224(data.getBytes());
    }

    public static String sha224(final byte[] data) {
        return bytes2HexString(sha224ToBytes(data));
    }

    public static byte[] sha224ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /*---------------------------------- SHA256 --------------------------------------*/

    public static String sha256(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha256(data.getBytes());
    }

    public static String sha256(final byte[] data) {
        return bytes2HexString(sha256ToBytes(data));
    }

    public static byte[] sha256ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /*---------------------------------- SHA384 --------------------------------------*/

    public static String sha384(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha384(data.getBytes());
    }

    public static String sha384(final byte[] data) {
        return bytes2HexString(sha384ToBytes(data));
    }

    public static byte[] sha384ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /*---------------------------------- SHA512 --------------------------------------*/

    public static String sha512(final String data) {
        if (data == null || data.length() == 0) return "";
        return sha512(data.getBytes());
    }

    public static String sha512(final byte[] data) {
        return bytes2HexString(sha512ToBytes(data));
    }

    public static byte[] sha512ToBytes(final byte[] data) {
        return hashTemplate(data, "SHA512");
    }

    /*---------------------------------- 内部方法 --------------------------------------*/

    public static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
