package me.shouheng.vmlib.component

import androidx.annotation.RestrictTo
import androidx.lifecycle.*
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.base.BaseViewModelOwner
import me.shouheng.vmlib.bean.*

/**
 * Combine multiple livedata in one: the observer of combined live data will
 * receive all data change events of [liveDataList].
 */
fun <T> combine(
    vararg liveDataList: LiveData<T>
): MediatorLiveData<T> {
    return MediatorLiveData<T>().apply {
        liveDataList.forEach {
            addSource(it) { value ->
                this.value = value
            }
        }
    }
}

/**
 * Make current livedata as a single live event live data.
 */
fun <T> AdvancedLiveData<T>.asSingle(): AdvancedLiveData<T> {
    return SingleLiveEventWrapper(this)
}

/** Kotlin DSL styled observe method. */
inline fun <T> LifecycleOwner.observeOn(
    liveData: LiveData<Resources<T>>,
    init    : LiveDataObserverBuilder<T>.() -> Unit
): Observer<Resources<T>> {
    val builder = LiveDataObserverBuilder(liveData)
    builder.apply(init)
    return builder.build(this)
}

/** Kotlin DSL styled observe method. */
inline fun <T> BaseViewModelOwner<*>.observeOn(
    liveData: LiveData<Resources<T>>,
    init    : LiveDataObserverBuilder<T>.() -> Unit
): Observer<Resources<T>> {
    val builder = LiveDataObserverBuilder(liveData)
    builder.apply(init)
    return builder.build(this)
}

/** Observe data */
inline fun <T> BaseViewModelOwner<*>.observeOn(
    dataType: Class<T>,
    sid     : Int? = null,
    init    : LiveDataObserverBuilder<T>.() -> Unit
): Observer<Resources<T>> {
    val viewModel = getViewModel()
    val livedata = viewModel.getObservable(dataType, sid)
    return observeOn(livedata, init)
}

/** Observe list data */
inline fun <T> BaseViewModelOwner<*>.observeOnList(
    dataType: Class<T>,
    sid     : Int? = null,
    init    : LiveDataObserverBuilder<List<T>>.() -> Unit
): Observer<Resources<List<T>>> {
    val viewModel = getViewModel()
    val livedata = viewModel.getListObservable(dataType, sid)
    return observeOn(livedata,  init)
}

/** Builder for livedata observer. */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class LiveDataObserverBuilder<T> constructor(private val liveData: LiveData<Resources<T>>) {
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
    fun onFailure(fail: (res: Resources<T>) -> Unit) {
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

    fun build(owner: LifecycleOwner): Observer<Resources<T>> = Observer<Resources<T>> { resources ->
        resources.onSuccess {
            success?.invoke(it)
        }.onLoading {
            loading?.invoke(it)
        }.onFailure {
            fail?.invoke(it)
        }.onProgress {
            progress?.invoke(it)
        }
    }.apply {
        if (sticky) {
            liveData.observe(owner, this)
        } else {
            if (liveData is AdvancedLiveData) {
                liveData.observe(owner, sticky, this)
            } else {
                L.w("Current livedata is not an implementation of AdvancedLiveData, " +
                        "the sticky action won't take into effect.")
                liveData.observe(owner, this)
            }
        }
    }
}
