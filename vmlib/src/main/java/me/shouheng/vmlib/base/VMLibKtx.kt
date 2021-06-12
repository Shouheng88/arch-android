package me.shouheng.vmlib.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status

/** Add observe method to lifecycle owner. */
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline onChanged: (t: T?)->Unit
) {
    liveData.observe(this, Observer<T> {
        onChanged(it)
    })
}

/** Aff observe method to lifecycle owner. */
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<Resources<T>>,
    crossinline success: (res: Resources<T>) -> Unit = {},
    crossinline fail: (res: Resources<T>) -> Unit = {},
    crossinline loading: (res: Resources<T>) -> Unit = {}
) {
    liveData.observe(this, Observer {
        when (it?.status) {
            Status.SUCCESS -> success(it)
            Status.LOADING -> loading(it)
            Status.FAILED -> fail(it)
        }
    })
}

