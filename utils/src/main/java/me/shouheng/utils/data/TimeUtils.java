package me.shouheng.utils.data;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.shouheng.utils.UtilsApp;
import me.shouheng.utils.constant.TimeConstants;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/5/12 16:07
 */
public final class TimeUtils {

    public static long now() {
        return System.currentTimeMillis();
    }

    public static String nowString() {
        return toString(System.currentTimeMillis(), getDefaultFormat());
    }

    public static String nowString(@NonNull final DateFormat format) {
        return toString(System.currentTimeMillis(), format);
    }

    public static Date nowDate() {
        return new Date();
    }

    /*---------------------------------- 转换 --------------------------------------*/

    public static String toString(final long millis) {
        return toString(millis, getDefaultFormat());
    }

    public static String toString(final long millis, @NonNull final DateFormat format) {
        return format.format(new Date(millis));
    }

    public static String toString(long millis, int flags) {
        return DateUtils.formatDateTime(UtilsApp.getApp(), millis, flags);
    }

    public static long toMillis(final String time) {
        return toMillis(time, getDefaultFormat());
    }

    public static long toMillis(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Date toDate(final String time) {
        return toDate(time, getDefaultFormat());
    }

    public static Date toDate(final String time, @NonNull final DateFormat format) {
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toString(final Date date) {
        return toString(date, getDefaultFormat());
    }

    public static String toString(final Date date, @NonNull final DateFormat format) {
        return format.format(date);
    }

    public static String toString(final Date date, int flags) {
        return toString(date.getTime(), flags);
    }

    public static long toMillis(final Date date) {
        return date.getTime();
    }

    public static Date toDate(final long millis) {
        return new Date(millis);
    }

    /*---------------------------------- 跨度 --------------------------------------*/

    public static long span(final String time1,
                            final String time2,
                            @TimeConstants.Unit final int unit) {
        return span(time1, time2, getDefaultFormat(), unit);
    }

    public static long span(final String time1,
                            final String time2,
                            @NonNull final DateFormat format,
                            @TimeConstants.Unit final int unit) {
        return toTimeSpan(toMillis(time1, format) - toMillis(time2, format), unit);
    }

    public static long span(final Date date1,
                            final Date date2,
                            @TimeConstants.Unit final int unit) {
        return toTimeSpan(toMillis(date1) - toMillis(date2), unit);
    }

    public static long span(final long millis1,
                            final long millis2,
                            @TimeConstants.Unit final int unit) {
        return toTimeSpan(millis1 - millis2, unit);
    }

    /*---------------------------------- 判断 --------------------------------------*/

    public static boolean isLeapYear(final String time) {
        return isLeapYear(toDate(time, getDefaultFormat()));
    }

    public static boolean isLeapYear(final String time, @NonNull final DateFormat format) {
        return isLeapYear(toDate(time, format));
    }

    public static boolean isLeapYear(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    public static boolean isLeapYear(final long millis) {
        return isLeapYear(toDate(millis));
    }

    public static boolean isLeapYear(final int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /*---------------------------------- 内部 --------------------------------------*/

    private static final ThreadLocal<SimpleDateFormat> SDF_THREAD_LOCAL = new ThreadLocal<>();

    private static SimpleDateFormat getDefaultFormat() {
        SimpleDateFormat simpleDateFormat = SDF_THREAD_LOCAL.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SDF_THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * TODO 下面只能算是一种计算方式，即指定时间跨度中天的个数，但是：
     * 1. 不到一天怎么算？
     * 2. 起止日期怎么算？
     */
    private static long toTimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    private TimeUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
