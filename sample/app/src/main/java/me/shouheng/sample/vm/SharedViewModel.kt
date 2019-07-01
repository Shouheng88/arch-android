package me.shouheng.sample.vm

import android.app.Application
import me.shouheng.mvvm.base.BaseViewModel

/**
 * Shared view model between fragment.
 *
 * @author Wngshhng 2019-6-29
 */
class SharedViewModel(application: Application) : BaseViewModel(application) {

    var shareValue: String? = null
}