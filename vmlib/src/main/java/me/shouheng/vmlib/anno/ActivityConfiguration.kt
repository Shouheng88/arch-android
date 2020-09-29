package me.shouheng.vmlib.anno

/**
 * The activity configuration annotation.
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-7-01
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ActivityConfiguration(
    /**
     * Will the event bus will be used in this view.
     *
     * @return true if you want to use event bus.
     */
    val useEventBus: Boolean = false,
    /**
     * Does the user need login to get into this activity.
     *
     * @return true if the user must be logined
     */
    val needLogin: Boolean = true,
    /**
     * Umeng configuration, use default value.
     *
     * @return umeng configuration
     */
    val umeng: UmengConfiguration = UmengConfiguration()
)