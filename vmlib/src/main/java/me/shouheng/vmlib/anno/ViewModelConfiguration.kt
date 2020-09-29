package me.shouheng.vmlib.anno

/**
 * VM 的配置类
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2020-01-10 21:38
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ViewModelConfiguration(
    /**
     * Will the event bus will be used in this vm.
     *
     * @return true if you want to use event bus.
     */
    val useEventBus: Boolean = false
)