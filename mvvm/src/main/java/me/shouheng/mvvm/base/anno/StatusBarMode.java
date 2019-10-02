package me.shouheng.mvvm.base.anno;

import android.support.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Status bar mode
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-02 12:42
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@IntDef(value = {StatusBarMode.DEFAULT, StatusBarMode.LIGHT, StatusBarMode.DARK})
public @interface StatusBarMode {

    /**
     * Default status bar color
     */
    int DEFAULT     = 0;

    /**
     * Light status bar
     */
    int LIGHT       = 1;

    /**
     * Dark status bar
     */
    int DARK        = 2;
}
