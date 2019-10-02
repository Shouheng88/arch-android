package me.shouheng.mvvm.base.anno;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The fragment configuration annotation.
 *
 * @author WngShhng 2019-6-29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FragmentConfiguration {

    /**
     * Should the view model be shared between fragments.
     *
     * @return true to share
     */
    boolean shareViewMode() default false;

    /**
     * Will the event bus will be used in this view.
     *
     * @return true if you want to use event bus.
     */
    boolean useEventBus() default false;

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
     * @return page name
     */
    String pageName() default "";

    /**
     * Whether the umeng analytics will be used.
     *
     * @return true if use umeng analytics
     */
    boolean useUmengManual() default true;
}
