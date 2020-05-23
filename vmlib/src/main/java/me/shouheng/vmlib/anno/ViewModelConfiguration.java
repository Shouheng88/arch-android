package me.shouheng.vmlib.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VM 的配置类
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-01-10 21:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ViewModelConfiguration {

    /**
     * Will the event bus will be used in this vm.
     *
     * @return true if you want to use event bus.
     */
    boolean useEventBus() default false;
}
