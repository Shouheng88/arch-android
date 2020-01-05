package me.shouheng.mvvm.base.anno;

import android.support.annotation.ColorInt;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Status bar configuration
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-01-05 11:46
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface StatusBarConfiguration {

    /**
     * Status bar light mode.
     *
     * @return status bar mode
     */
    @StatusBarMode int statusBarMode() default StatusBarMode.DEFAULT;

    /**
     * Set status bar color, you can set any color except -1.
     *
     * NOTE: YOU NEED TO CALL {@link me.shouheng.utils.ui.BarUtils#addMarginTopEqualStatusBarHeight(View)}
     * MANUALLY IN YOUR ACTIVITY, OR USE "android:fitsSystemWindows="true"" IN YOUR ROOT LAYOUT OF
     * ACTIVITY. OTHERWISE, THE ACTIVITY WILL BE TRANSPARENT STATUS BAR.
     *
     * @return status bar color
     */
    @ColorInt int statusBarColor() default -1;
}
