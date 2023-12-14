package me.shouheng.vmlib.base

import android.app.Application
import androidx.annotation.RestrictTo
import androidx.lifecycle.AndroidViewModel
import me.shouheng.vmlib.anno.ViewModelConfiguration
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bus.Bus
import me.shouheng.vmlib.component.IntermediateLiveData
import me.shouheng.vmlib.component.LiveDataPool
import me.shouheng.vmlib.component.ResourceLiveData

/**
 * Basic implementation of common ViewModel.
 *
 * The view model has no pre-defined model associated, for MVVMs don't want to take care of the
 * model logic. You can get the data source anywhere, which is perhaps useful for small project.
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-6-29
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val liveDataPool = LiveDataPool<Any>()

    private val listLiveDataPool: LiveDataPool<*> = LiveDataPool<List<*>>()

    private val intermediateLiveData = IntermediateLiveData()

    /** If the view model use event bus */
    private var useEventBus = false

    /** Get a livedata of given type and sid which is used to distinguish two livedata of same type. */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun <T> getObservable(dataType: Class<T>, sid: Int? = null): ResourceLiveData<T> {
        return liveDataPool.acquireLiveData(dataType, sid, intermediateLiveData) as ResourceLiveData<T>
    }

    /** Get a livedata of given list type and sid which is used to distinguish two livedata of same type. */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun <T> getListObservable(dataType: Class<T>, sid: Int? = null): ResourceLiveData<List<T>> {
        return listLiveDataPool.acquireLiveData(dataType, sid, intermediateLiveData) as ResourceLiveData<List<T>>
    }

    /** Get a value base on [dataType] and [sid]. */
    fun <T> getValue(dataType: Class<T>, sid: Int? = null): T? = getObservable(dataType, sid).value?.data

    /** Get a list value base on [dataType] and [sid]. */
    fun <T> getListValue(dataType: Class<T>, sid: Int? = null): List<T>? = getListObservable(dataType, sid).value?.data

    /** Use resource to notify the value. */
    protected fun <T> setResources(
        dataType: Class<T>,
        resources: Resources<T>,
        sid: Int? = null,
    ) {
        getObservable(dataType, sid).value = resources
    }

    /** Set success state of data type */
    protected fun <T> setSuccess(
        dataType: Class<T>,
        data: T?,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getObservable(dataType, sid).value = Resources.success(data, udf1, udf2, udf3, udf4, udf5) }

    /** Set progress state of data type */
    protected fun <T> setProgress(
        dataType: Class<T>,
        data: T?,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getObservable(dataType, sid).value = Resources.progress(data, udf1, udf2, udf3, udf4, udf5) }

    /** Set loading state of data type */
    protected fun <T> setLoading(
        dataType: Class<T>,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getObservable(dataType, sid).value = Resources.loading(udf1, udf2, udf3, udf4, udf5) }

    /** Set fail state of data type */
    protected fun <T> setFailure(
        dataType: Class<T>,
        code: String?,
        message: String?,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getObservable(dataType, sid).value = Resources.failure(code, message, udf1, udf2, udf3, udf4, udf5) }

    /** Use resource to notify the value. */
    protected fun <T> setListResources(
        dataType: Class<T>,
        resources: Resources<List<T>>,
        sid: Int?       = null,
    ) {
        getListObservable(dataType, sid).value = resources
    }

    /** Set success state of list data type */
    protected fun <T> setListSuccess(
        dataType: Class<T>,
        data: List<T>?,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getListObservable(dataType, sid).value = Resources.success(data, udf1, udf2, udf3, udf4, udf5) }

    /** Set progress state of list data type */
    protected fun <T> setListProgress(
        dataType: Class<T>,
        data: List<T>?,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getListObservable(dataType, sid).value = Resources.progress(data, udf1, udf2, udf3, udf4, udf5) }

    /** Set loading state of list data type */
    protected fun <T> setListLoading(
        dataType: Class<T>,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getListObservable(dataType, sid).value = Resources.loading(udf1, udf2, udf3, udf4, udf5) }

    /** Set fail state of list data type */
    protected fun <T> setListFailure(
        dataType: Class<T>,
        code: String?,
        message: String?,
        sid: Int?       = null,
        udf1: Long?     = null,
        udf2: Double?   = null,
        udf3: Boolean?  = null,
        udf4: String?   = null,
        udf5: Any?      = null
    ) { getListObservable(dataType, sid).value = Resources.failure(code, message, udf1, udf2, udf3, udf4, udf5) }

    override fun onCleared() {
        if (useEventBus) {
            Bus.get().unregister(this)
        }
    }

    init {
        val configuration = this.javaClass.getAnnotation(ViewModelConfiguration::class.java)
        if (configuration != null) {
            useEventBus = configuration.useEventBus
            if (useEventBus) {
                Bus.get().register(this)
            }
        }
    }
}
