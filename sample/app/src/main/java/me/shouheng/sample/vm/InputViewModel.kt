package me.shouheng.sample.vm

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
class InputViewModel(application: Application) : BaseViewModel(application) {

    /** Notify the title has changed. */
    fun setTitle(title: String) {
        setSuccess(String::class.java, title, SID_TITLE)
    }

    /** Notify the content has changed. */
    fun setContent(content: String) {
        setSuccess(String::class.java, content, SID_CONTENT)
    }

    companion object {
        const val SID_TITLE     = 1
        const val SID_CONTENT   = 2
    }
}
