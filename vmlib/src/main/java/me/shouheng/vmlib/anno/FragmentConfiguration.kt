package me.shouheng.vmlib.anno

/**
 * The fragment configuration annotation.
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-6-29
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class FragmentConfiguration(

    /** Should the view model be shared between fragments. */
    val shareViewModel: Boolean = false,

    /** Will the event bus will be used in this view. */
    val useEventBus: Boolean = false,

    /** Umeng configuration. */
    val umeng: UmengConfiguration = UmengConfiguration()
)