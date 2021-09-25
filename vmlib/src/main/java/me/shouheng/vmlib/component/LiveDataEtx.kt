package me.shouheng.vmlib.component

import androidx.lifecycle.*
import me.shouheng.vmlib.base.BaseActivity
import me.shouheng.vmlib.base.BaseFragment
import me.shouheng.vmlib.base.BaseViewModelOwner
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status

/** Add observe method to lifecycle owner. */
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline onChanged: (t: T?)->Unit
) {
    liveData.observe(this, {
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
    crossinline fail   : (res: Resources<T>) -> Unit,
    crossinline loading: (res: Resources<T>) -> Unit
) {
    liveData.observe(this, {
        when (it?.status) {
            Status.SUCCESS -> success(it)
            Status.LOADING -> loading(it)
            Status.FAILED  -> fail(it)
        }
    })
}

/** Kotlin DSL styled observe method. */
fun <T> LifecycleOwner.observeOn(
    liveData: SingleLiveEvent<Resources<T>>,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    val builder = LiveDataObserverBuilder<T>()
    builder.apply(init)
    if (builder.stickyObserver) {
        liveData.observe(this, builder.build())
    } else {
        liveData.observe(this, builder.stickyObserver, builder.build())
    }
}

/** Observe on data type. */
fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(getViewModel().getObservable(dataType), init)
}

/** Observe data */
fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(dataType, null, single, init)
}

/** Observe data */
fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    sid     : Int? = null,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(dataType, sid, false, init)
}

/** Observe data */
fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    sid     : Int? = null,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(getViewModel().getObservable(dataType, sid, single), init)
}

/** Observe list data */
fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOnList(dataType, null, false, init)
}

/** Observe list data */
fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOnList(dataType, null, single, init)
}

/** Observe list data */
fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    sid     : Int? = null,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOnList(dataType, sid, false, init)
}

/** Observe list data */
fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    sid     : Int? = null,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOn(getViewModel().getListObservable(dataType, sid, single), init)
}

/** Builder for livedata observer. */
class LiveDataObserverBuilder<T> {
    private var success: ((res: Resources<T>) -> Unit)? = null
    private var fail   : ((res: Resources<T>) -> Unit)? = null
    private var loading: ((res: Resources<T>) -> Unit)? = null
    internal var stickyObserver: Boolean = false

    /** To decide if observe the data as sticky. */
    fun withSticky(sticky: Boolean) {
        stickyObserver = sticky
    }

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
