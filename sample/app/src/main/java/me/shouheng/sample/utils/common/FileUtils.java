package me.shouheng.sample.utils.common;

import android.os.Environment;

import java.io.File;

public final class FileUtils {

    private static final String FILE_SEP       = System.getProperty("file.separator");

    private static boolean isStorateReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private static boolean isStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getExternalStoragePublicDir() {
        String path = Environment.getExternalStorageDirectory() + FILE_SEP + Constants.PUBLIC_FILE_DIR + FILE_SEP;
        File dir = null;
        if (isStorageWritable()) {
            dir = new File(path);
            if (!dir.exists()) dir.mkdirs();
        }
        return dir;
    }

    public static File getExternalStoragePublicCrashDir() {
        File crashDir = null;
        if (isStorageWritable()) {
            crashDir = new File(getExternalStoragePublicDir() + FILE_SEP + Constants.PUBLIC_CRASH_LOG_DIR);
            if (!crashDir.exists()) crashDir.mkdirs();
        }
        return crashDir;
    }

    public static File getExternalStoragePublicLogDir() {
        File crashDir = null;
        if (isStorageWritable()) {
            crashDir = new File(getExternalStoragePublicDir() + FILE_SEP + Constants.PUBLIC_LOG_DIR);
            if (!crashDir.exists()) crashDir.mkdirs();
        }
        return crashDir;
    }
}
