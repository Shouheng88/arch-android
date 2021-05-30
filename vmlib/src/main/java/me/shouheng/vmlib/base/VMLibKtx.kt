package me.shouheng.vmlib.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

/** Add observe method to lifecycle owner. */
inline fun <T> LifecycleOwner.observe(liveData: MutableLiveData<T>, crossinline observer: (t: T?)->Unit) {
    liveData.observe(this, Observer<T> {
        observer(it)
    })
}
