package me.shouheng.mvvm.base.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The fragment configuration annotation.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FragmentConfiguration {

    /**
     * Should the view model be shared between fragments.
     *
     * @return true to share
     */
    boolean shareViewModel() default false;

    /**
     * Will the event bus will be used in this view.
     *
     * @return true if you want to use event bus.
     */
    boolean useEventBus() default false;

    /**
     * Umeng configuration
     *
     * @return umeng configuration
     */
    UmengConfiguration umengConfiguration() default @UmengConfiguration;
}
