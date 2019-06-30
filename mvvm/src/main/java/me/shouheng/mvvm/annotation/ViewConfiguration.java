package me.shouheng.mvvm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The view configuration annotation.
 *
 * @author WngShhng 2019-6-29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ViewConfiguration {

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
