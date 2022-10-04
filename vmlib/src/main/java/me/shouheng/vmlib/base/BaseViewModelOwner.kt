package me.shouheng.vmlib.base

import androidx.lifecycle.LifecycleOwner

/**
 * Owner of [BaseViewModel].
 *
 * @Author wangshouheng
 * @Time 2021/9/24
 */
interface BaseViewModelOwner<U : BaseViewModel> : LifecycleOwner {

    /** Get view model. */
    fun getViewModel(): U
}
