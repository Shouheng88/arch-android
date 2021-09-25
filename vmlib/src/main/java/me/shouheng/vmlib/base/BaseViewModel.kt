package me.shouheng.vmlib.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import me.shouheng.vmlib.anno.ViewModelConfiguration
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bus.Bus
import me.shouheng.vmlib.component.LiveDataHolder

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
    private val holder = LiveDataHolder<Any>()
    private val listHolder: LiveDataHolder<*> = LiveDataHolder<List<*>>()

    /** If the view model use event bus */
    private var useEventBus = false

    /**
     * Get the LiveData of given type and flag
     *
     * @param dataType the data type
     * @param sid      the identity of live data, used to distinguish two livedata of same data type
     * @param single   is single event
     * @param <T>      the generic data type.
     * @return         the live data.
     * @see SingleLiveEvent
     */
    fun <T> getObservable(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false
    ): MutableLiveData<Resources<T>> {
        return holder.getLiveData(dataType, sid, single) as MutableLiveData<Resources<T>>
    }

    /**
     * Get the LiveData of given list type and flag
     *
     * @param dataType the data type
     * @param sid      the identity of live data, used to distinguish two livedata of same data type
     * @param single   is single event
     * @param <T>      the generic data type.
     * @return         the live data.
     * @see SingleLiveEvent
     */
    fun <T> getListObservable(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false
    ): MutableLiveData<Resources<List<T>>> {
        return listHolder.getLiveData(dataType, sid, single) as MutableLiveData<Resources<List<T>>>
    }

    /** Set success state of data type */
    fun <T> setSuccess(
        dataType: Class<T>,
        data: T,
        sid: Int? = null,
        single: Boolean = false,
        udf1: Long? = null,
        udf2: Double? = null,
        udf3: Boolean? = null,
        udf4: String? = null,
        udf5: Any? = null
    ) {
        getObservable(dataType, sid, single).value = Resources.success(data, udf1, udf2, udf3, udf4, udf5)
    }

    /** Set loading state of data type */
    fun <T> setLoading(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false,
        udf1: Long? = null,
        udf2: Double? = null,
        udf3: Boolean? = null,
        udf4: String? = null,
        udf5: Any? = null
    ) {
        getObservable(dataType, sid, single).value = Resources.loading(udf1, udf2, udf3, udf4, udf5)
    }

    /** Set fail state of data type */
    fun <T> setFailed(
        dataType: Class<T>,
        code: String?,
        message: String?,
        sid: Int? = null,
        single: Boolean = false,
        udf1: Long? = null,
        udf2: Double? = null,
        udf3: Boolean? = null,
        udf4: String? = null,
        udf5: Any? = null
    ) {
        getObservable(dataType, sid, single).value = Resources.failed(code, message, udf1, udf2, udf3, udf4, udf5)
    }

    /** Set success state of list data type */
    fun <T> setListSuccess(
        dataType: Class<T>,
        data: List<T>,
        sid: Int? = null,
        single: Boolean = false,
        udf1: Long? = null,
        udf2: Double? = null,
        udf3: Boolean? = null,
        udf4: String? = null,
        udf5: Any? = null
    ) {
        getListObservable(dataType, sid, single).value = Resources.success(data, udf1, udf2, udf3, udf4, udf5)
    }

    /** Set loading state of list data type */
    fun <T> setListLoading(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false,
        udf1: Long? = null,
        udf2: Double? = null,
        udf3: Boolean? = null,
        udf4: String? = null,
        udf5: Any? = null
    ) {
        getListObservable(dataType, sid, single).value = Resources.loading(udf1, udf2, udf3, udf4, udf5)
    }

    /** Set fail state of list data type */
    fun <T> setListFailed(
        dataType: Class<T>,
        code: String?,
        message: String?,
        sid: Int? = null,
        single: Boolean = false,
        udf1: Long? = null,
        udf2: Double? = null,
        udf3: Boolean? = null,
        udf4: String? = null,
        udf5: Any? = null
    ) {
        getListObservable(dataType, sid, single).value = Resources.failed(code, message, udf1, udf2, udf3, udf4, udf5)
    }

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