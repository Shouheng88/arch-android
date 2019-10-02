package me.shouheng.mvvm.base.anno;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The activity configuration annotation.
 *
 * @author WngShhng 2019-7-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ActivityConfiguration {

    /**
     * Will the event bus will be used in this view.
     *
     * @return true if you want to use event bus.
     */
    boolean useEventBus() default false;

    /**
     * Does the user need login to get into this activity.
     *
     * @return true if the user must be logined
     */
    boolean needLogin() default true;

    /**
     * Get layout resource id.
     *
     * @return the layout resource id.
     */
    @LayoutRes int layoutResId() default 0;

    /**
     * The page name used for umeng etc. The activity or fragment class simple name will
     * be used if you didn't set this value.
     *
     * @return the page name
     */
    String pageName() default "";

    /**
     * Whether current activity contains fragment
     *
     * @return true if contains
     */
    boolean hasFragment() default false;

    /**
     * Whether the umeng analytics will be used.
     *
     * @return true if use umeng analytics
     */
    boolean useUmengManual() default true;
}
