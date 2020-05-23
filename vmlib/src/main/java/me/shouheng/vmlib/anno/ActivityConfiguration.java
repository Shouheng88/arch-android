package me.shouheng.vmlib.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The activity configuration annotation.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-7-01
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
     * Umeng configuration, use default value.
     *
     * @return umeng configuration
     */
    UmengConfiguration umeng() default @UmengConfiguration;
}
