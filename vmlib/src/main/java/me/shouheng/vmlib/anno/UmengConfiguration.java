package me.shouheng.vmlib.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * UMeng configuration
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-01-05 11:53
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface UmengConfiguration {

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
    boolean useUmengManual() default false;

}
