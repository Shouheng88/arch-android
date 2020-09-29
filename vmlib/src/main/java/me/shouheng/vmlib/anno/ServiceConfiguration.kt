package me.shouheng.vmlib.anno

/**
 * Configuration annotation for Android Service.
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-7-1
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ServiceConfiguration(
    /**
     * Will the event bus will be used in this view.
     *
     * @return true if you want to use event bus.
     */
    val useEventBus: Boolean = false
)