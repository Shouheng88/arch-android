package me.shouheng.vmlib.component

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import me.shouheng.vmlib.base.BaseViewModelOwner
import me.shouheng.vmlib.bean.*

/** Add observe method to lifecycle owner. */
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline onChanged: (t: T?)->Unit
) {
    liveData.observe(this) {
        onChanged(it)
    }
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
    liveData.observe(this) {
        it.onSuccess(success).onLoading(loading).onFailed(fail)
    }
}

/** Kotlin DSL styled observe method. */
inline fun <T> LifecycleOwner.observeOn(
    liveData: LiveData<Resources<T>>,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(liveData, null, init)
}

/** Kotlin DSL styled observe method. */
inline fun <T> LifecycleOwner.observeOn(
    liveData: LiveData<Resources<T>>,
    sticky  : Boolean? = null,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    val builder = LiveDataObserverBuilder<T>()
    builder.apply(init)
    // To observe livedata base on the livedata type and 'stickyObserver' field.
    // Only when the observe type is not sticky and the livedata is unpeek type,
    // the none-sticky will take into effect.
    if ((!builder.sticky || sticky == false) && liveData is UnPeekLiveData) {
        liveData.observe(this, false, builder.build())
    } else {
        liveData.observe(this, builder.build())
    }
}

/** Observe on data type. */
inline fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(dataType, null, false, init)
}

/** Observe data */
inline fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(dataType, null, single, init)
}

/** Observe data */
inline fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    sid     : Int? = null,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(dataType, sid, false, init)
}

/** Observe data */
inline fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    sid     : Int? = null,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<T>.() -> Unit
) {
    observeOn(getViewModel().getObservable(dataType, sid, single, Status.SUCCESS), init)
    observeOn(getViewModel().getObservable(dataType, sid, single, null), false, init)
}

/** Observe list data */
inline fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOnList(dataType, null, false, init)
}

/** Observe list data */
inline fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOnList(dataType, null, single, init)
}

/** Observe list data */
inline fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    sid     : Int? = null,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOnList(dataType, sid, false, init)
}

/** Observe list data */
inline fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    sid     : Int? = null,
    single  : Boolean = false,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
) {
    observeOn(getViewModel().getListObservable(dataType, sid, single, Status.SUCCESS),  init)
    observeOn(getViewModel().getListObservable(dataType, sid, single, null),  false, init)
}

/** Builder for livedata observer. */
class LiveDataObserverBuilder<T> {
    private var success : ((res: Resources<T>) -> Unit)? = null
    private var fail    : ((res: Resources<T>) -> Unit)? = null
    private var loading : ((res: Resources<T>) -> Unit)? = null
    private var progress: ((res: Resources<T>) -> Unit)? = null

    var sticky: Boolean = true
        private set

    /** To decide if observe the data as sticky. */
    fun withSticky(sticky: Boolean) {
        this.sticky = sticky
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

    /** Called when progress changed. */
    fun onProgress(progress: (res: Resources<T>) -> Unit) {
        this.progress = progress
    }

    fun build(): Observer<Resources<T>> = Observer { resources ->
        resources.onSuccess {
            success?.invoke(it)
        }.onLoading {
            loading?.invoke(it)
        }.onFailed {
            fail?.invoke(it)
        }.onProgress {
            progress?.invoke(it)
        }
    }
}
