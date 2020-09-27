package me.shouheng.vmlib.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.shouheng.vmlib.anno.ViewModelConfiguration;
import me.shouheng.vmlib.bean.Resources;
import me.shouheng.vmlib.bus.Bus;
import me.shouheng.vmlib.holder.LiveDataHolder;

/**
 * Basic implementation of common ViewModel.
 *
 * The view model has no pre-defined model associated, for MVVMs don't want to take care of the
 * model logic. You can get the data source anywhere, which is perhaps useful for small project.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public class BaseViewModel extends AndroidViewModel {

    private LiveDataHolder holder = new LiveDataHolder();

    private LiveDataHolder listHolder = new LiveDataHolder<List>();

    /**
     * If the view model use event bus
     */
    private boolean useEventBus = false;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    {
        ViewModelConfiguration configuration = this.getClass().getAnnotation(ViewModelConfiguration.class);
        if (configuration != null) {
            useEventBus = configuration.useEventBus();
            if (useEventBus) {
                Bus.get().register(this);
            }
        }
    }

    /**
     * Get the LiveData of given type.
     *
     * @param dataType the data type.
     * @param <T>      the data type.
     * @return         the live data.
     */
    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType) {
        return holder.getLiveData(dataType, false);
    }

    /**
     * Get the LiveData of given type.
     *
     * @param dataType the data type.
     * @param single   is single event
     * @param <T>      the data type.
     * @return         the live data.
     * @see SingleLiveEvent
     */
    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType, boolean single) {
        return holder.getLiveData(dataType, single);
    }

    /**
     * Get the LiveData of given type and flag
     *
     * @param dataType the data type
     * @param flag     the flag
     * @param <T>      the generic data type.
     * @return         the live data.
     */
    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType, int flag) {
        return holder.getLiveData(dataType, flag, false);
    }

    /**
     * et the LiveData of given type and flag
     *
     * @param dataType the data type
     * @param flag     the flag
     * @param single   is single event
     * @param <T>      the generic data type.
     * @return         the live data.
     * @see SingleLiveEvent
     */
    public <T> MutableLiveData<Resources<T>> getObservable(Class<T> dataType, int flag, boolean single) {
        return holder.getLiveData(dataType, flag, single);
    }

    /**
     * Get the LiveData of given list type.
     *
     * @param dataType the data type of element in list.
     * @param <T>      the generic data type.
     * @return         the live data.
     */
    public <T> MutableLiveData<Resources<List<T>>> getListObservable(Class<T> dataType) {
        return listHolder.getLiveData(dataType, false);
    }

    /**
     * Get the LiveData of given list type.
     *
     * @param dataType the data type of element in list.
     * @param single   is single event
     * @param <T>      the generic data type.
     * @return         the live data.
     * @see SingleLiveEvent
     */
    public <T> MutableLiveData<Resources<List<T>>> getListObservable(Class<T> dataType, boolean single) {
        return listHolder.getLiveData(dataType, single);
    }

    /**
     * Get the LiveData of given list type and flag
     *
     * @param dataType the data type
     * @param flag     the flag
     * @param <T>      the generic data type.
     * @return         the live data.
     */
    public <T> MutableLiveData<Resources<List<T>>> getListObservable(Class<T> dataType, int flag) {
        return listHolder.getLiveData(dataType, flag, false);
    }

    /**
     * Get the LiveData of given list type and flag
     *
     * @param dataType the data type
     * @param flag     the flag
     * @param single   is single event
     * @param <T>      the generic data type.
     * @return         the live data.
     * @see SingleLiveEvent
     */
    public <T> MutableLiveData<Resources<List<T>>> getListObservable(Class<T> dataType, int flag, boolean single) {
        return listHolder.getLiveData(dataType, flag, single);
    }

    /**
     * Notify the observer the success state.
     *
     * @param dataType data type
     * @param data     data
     * @param <T>      data type
     */
    public <T> void setSuccess(Class<T> dataType, T data) {
        getObservable(dataType).setValue(Resources.success(data));
    }

    /** @see #setSuccess(Class, Object) */
    public <T> void setLoading(Class<T> dataType) {
        getObservable(dataType).setValue(Resources.<T>loading());
    }

    /** @see #setSuccess(Class, Object) */
    public <T> void setFailed(Class<T> dataType, String code, String message) {
        getObservable(dataType).setValue(Resources.<T>failed(code, message));
    }

    /**
     * Notify the observer the success state.
     *
     * @param dataType data type
     * @param data     data
     * @param <T>      data type
     */
    public <T> void setListSuccess(Class<T> dataType, List<T> data) {
        getListObservable(dataType).setValue(Resources.success(data));
    }

    /** @see #setListSuccess(Class, List) */
    public <T> void setListLoading(Class<T> dataType) {
        getListObservable(dataType).setValue(Resources.<List<T>>loading());
    }

    /** @see #setListSuccess(Class, List) */
    public <T> void setListFailed(Class<T> dataType, String code, String message) {
        getListObservable(dataType).setValue(Resources.<List<T>>failed(code, message));
    }

    /**
     * Post one event by EventBus
     *
     * @param event the event to post
     */
    protected void post(Object event) {
        Bus.get().post(event);
    }

    /**
     * Post one sticky event by EventBus
     *
     * @param event the sticky event
     */
    protected void postSticky(Object event) {
        Bus.get().postSticky(event);
    }

    @Override
    protected void onCleared() {
        if (useEventBus) {
            Bus.get().unregister(this);
        }
    }
}
