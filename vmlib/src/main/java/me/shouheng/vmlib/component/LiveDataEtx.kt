package me.shouheng.vmlib.component

import androidx.lifecycle.*
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

/**
 * Add observe method to lifecycle owner. Callbacks of [success], [fail] and [loading]
 * are required, since, otherwise, it might be ambiguous with another [observe] method.
 */
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<Resources<T>>,
    crossinline success: (res: Resources<T>) -> Unit,
    crossinline fail:    (res: Resources<T>) -> Unit,
    crossinline loading: (res: Resources<T>) -> Unit
) {
    liveData.observe(this, Observer {
        when (it?.status) {
            Status.SUCCESS -> success(it)
            Status.LOADING -> loading(it)
            Status.FAILED  -> fail(it)
        }
    })
}

/** Kotlin DSL styled observe method. */
fun <T> LifecycleOwner.observeOn(
    liveData: LiveData<Resources<T>>,
    init: LiveDataObserverBuilder<T>.() -> Unit
) {
    val builder = LiveDataObserverBuilder<T>()
    builder.apply(init)
    liveData.observe(this, builder.build())
}

/** Builder for livedata observer. */
class LiveDataObserverBuilder<T> {
    private var success: ((res: Resources<T>) -> Unit)? = null
    private var fail   : ((res: Resources<T>) -> Unit)? = null
    private var loading: ((res: Resources<T>) -> Unit)? = null

    /** Called when livedata is success. */
    fun onSuccess(success: (res: Resources<T>) -> Unit) {
        this.success = success
    }

    /** Called when livedata is fail. */
    fun onFail(fail: (res: Resources<T>) -> Unit) {
        this.fail = fail
    }

    /** Called when livedata is loading. */
    fun onLoading(loading: (res: Resources<T>) -> Unit) {
        this.loading = loading
    }

    internal fun build(): Observer<Resources<T>> = Observer {
        when (it?.status) {
            Status.SUCCESS -> success?.invoke(it)
            Status.LOADING -> loading?.invoke(it)
            Status.FAILED  -> fail?.invoke(it)
        }
    }
}
