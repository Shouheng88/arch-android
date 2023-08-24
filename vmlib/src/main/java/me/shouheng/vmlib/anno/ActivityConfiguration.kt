package me.shouheng.vmlib.anno

import me.shouheng.utils.constant.ActivityDirection

/**
 * The activity configuration annotation.
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-7-01
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ActivityConfiguration(

    /** Will the event bus will be used in this view. */
    val useEventBus: Boolean = false,

    /** The activity exit direction */
    @ActivityDirection val exitDirection: Int = ActivityDirection.ANIMATE_EASE_IN_OUT
)