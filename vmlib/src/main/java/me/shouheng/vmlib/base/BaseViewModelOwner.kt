package me.shouheng.vmlib.base

import androidx.lifecycle.LifecycleOwner
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status
import me.shouheng.vmlib.component.observe

/**
 * Owner of [BaseViewModel].
 *
 * @Author wangshouheng
 * @Time 2021/9/24
 */
interface BaseViewModelOwner<U : BaseViewModel> : LifecycleOwner {

    /** Get view model. */
    fun getViewModel(): U

    /** Observe data */
    fun <T> observe(
        dataType: Class<T>,
        success: (res: Resources<T>) -> Unit = {},
        fail:    (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(dataType, null, false, success, fail, loading)
    }

    /** Observe data */
    fun <T> observe(
        dataType: Class<T>,
        single: Boolean = false,
        success: (res: Resources<T>) -> Unit = {},
        fail:    (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(dataType, null, single, success, fail, loading)
    }

    /** Observe data */
    fun <T> observe(
        dataType: Class<T>,
        sid: Int? = null,
        success: (res: Resources<T>) -> Unit = {},
        fail:    (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(dataType, sid, false, success, fail, loading)
    }

    /** Observe data */
    fun <T> observe(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false,
        success: (res: Resources<T>) -> Unit = {},
        fail:    (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(getViewModel().getObservable(dataType, sid, single, Status.SUCCESS), success, fail, loading)
        observe(getViewModel().getObservable(dataType, sid, single, null), success, fail, loading)
    }

    /** Observe list data */
    fun <T> observeList(
        dataType: Class<T>,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail:    (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observeList(dataType, null, false, success, fail, loading)
    }

    /** Observe list data */
    fun <T> observeList(
        dataType: Class<T>,
        single: Boolean = false,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail:    (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observeList(dataType, null, single, success, fail, loading)
    }

    /** Observe list data */
    fun <T> observeList(
        dataType: Class<T>,
        sid: Int? = null,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail:    (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observeList(dataType, sid, false, success, fail, loading)
    }

    /** Observe list data */
    fun <T> observeList(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail:    (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observe(getViewModel().getListObservable(dataType, sid, single, Status.SUCCESS), success, fail, loading)
        observe(getViewModel().getListObservable(dataType, sid, single, null), success, fail, loading)
    }
}