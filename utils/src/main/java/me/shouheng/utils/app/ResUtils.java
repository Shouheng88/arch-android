package me.shouheng.utils.app;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FontRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import java.io.InputStream;

import me.shouheng.utils.UtilsApp;

/**
 * @author WngShhng 2019-05-08 20:20
 */
public final class ResUtils {

    /*----------------------------------wrapper methods--------------------------------------*/

    public static int[] getIntArray(@ArrayRes int id) {
        return UtilsApp.getApp().getResources().getIntArray(id);
    }

    public static CharSequence[] getTextArray(@ArrayRes int id) {
        return UtilsApp.getApp().getResources().getTextArray(id);
    }

    public static String[] getStringArray(@ArrayRes int id) {
        return UtilsApp.getApp().getResources().getStringArray(id);
    }

    @ColorInt
    public static int getColor(@ColorRes int id) {
        return UtilsApp.getApp().getResources().getColor(id);
    }

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

    public static Drawable getDrawable(@DrawableRes int id) {
        return UtilsApp.getApp().getResources().getDrawable(id);
    }

    @TargetApi(VERSION_CODES.O)
    public static Typeface getFont(@FontRes int id) {
        return UtilsApp.getApp().getResources().getFont(id);
    }

    public static float getDimension(@DimenRes int id) {
        return UtilsApp.getApp().getResources().getDimension(id);
    }

    public static boolean getBoolean(@BoolRes int id) {
        return UtilsApp.getApp().getResources().getBoolean(id);
    }

    public static int getInteger(@IntegerRes int id) {
        return UtilsApp.getApp().getResources().getInteger(id);
    }

    public static InputStream openRawResource(@RawRes int id) {
        return UtilsApp.getApp().getResources().openRawResource(id);
    }

    public static AssetFileDescriptor openRawResourceFd(@RawRes int id) {
        return UtilsApp.getApp().getResources().openRawResourceFd(id);
    }

    public static AssetManager getAssets() {
        return UtilsApp.getApp().getResources().getAssets();
    }

    /*----------------------------------inner methods--------------------------------------*/

    private ResUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }

}
