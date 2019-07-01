package me.shouheng.mvvm.base.anno;

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
}
