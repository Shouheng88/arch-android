package me.shouheng.vmlib.anno

/**
 * UMeng configuration
 *
 * @author [WngShhng](mailto:shouheng2020@gmail.com)
 * @version 2020-01-05 11:53
 */
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class UmengConfiguration(
    /**
     * The page name used for umeng etc. The activity or fragment class simple name will
     * be used if you didn't set this value.
     */
    val pageName: String = "",

    /** Whether current activity contains fragment */
    val fragmentActivity: Boolean = false,

    /** Whether the umeng analytics will be used manually. */
    val useUmengManual: Boolean = false
)