package me.shouheng.vmlib.anno

/**
 * ViewModel configuration annotation.
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2020-01-10 21:38
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class ViewModelConfiguration(

    /** Will the event bus will be used in this vm.*/
    val useEventBus: Boolean = false
)