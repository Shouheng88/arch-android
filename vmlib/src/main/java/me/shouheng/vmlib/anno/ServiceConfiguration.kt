package me.shouheng.vmlib.anno

/**
 * Configuration annotation for Android Service.
 *
 * @author [WngShhng](mailto:shouheng2020@gmail.com)
 * @version 2019-7-1
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ServiceConfiguration(

    /** Will the event bus will be used in this view. */
    val useEventBus: Boolean = false
)