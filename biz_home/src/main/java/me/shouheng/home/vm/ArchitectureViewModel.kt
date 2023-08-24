package me.shouheng.home.vm

import android.app.Application
import me.shouheng.vmlib.base.BaseViewModel

/**
 * Input viewmodel sample. This viewmodel is used to show usage of 'sid' when
 * trying to get a livedata from viewmode by calling [BaseViewModel.getObservable]
 * methods.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
class ArchitectureViewModel(application: Application) : BaseViewModel(application) {

    /** Notify the title has changed. */
    fun setTitle(title: String) {
        setSuccess(String::class.java, title, ARCHITECTURE_SAMPLE_SID_TITLE)
    }

    /** Notify the content has changed. */
    fun setContent(content: String) {
        setSuccess(String::class.java, content, ARCHITECTURE_SAMPLE_SID_CONTENT)
    }

    /** Cause title failure. */
    fun causeTitleFailure() {
        setFailure(String::class.java, "-1", "mocked title failed", ARCHITECTURE_SAMPLE_SID_TITLE)
    }

    /** Cause content failure. */
    fun causeContentFailure() {
        setFailure(String::class.java, "-1", "mocked content failed", ARCHITECTURE_SAMPLE_SID_CONTENT)
    }

    companion object {
        const val ARCHITECTURE_SAMPLE_SID_TITLE     = 1
        const val ARCHITECTURE_SAMPLE_SID_CONTENT   = 2
    }
}
