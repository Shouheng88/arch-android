package me.shouheng.mvvm.base;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shouheng.mvvm.base.anno.ViewModelConfiguration;
import me.shouheng.mvvm.bean.Resources;
import me.shouheng.mvvm.bus.Bus;

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

    private Map<Class, MutableLiveData> liveDataMap = new HashMap<>();

    private Map<Class, MutableLiveData> listLiveDataMap = new HashMap<>();

    private Map<Class, SparseArray<MutableLiveData>> sparseIntArrayMap = new HashMap<>();

    private Map<Class, SparseArray<MutableLiveData>> listSparseIntArrayMap = new HashMap<>();

    /**
     * 当前 VM 是否使用 EventBus
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
        MutableLiveData<Resources<T>> liveData = liveDataMap.get(dataType);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            liveDataMap.put(dataType, liveData);
        }
        return liveData;
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
        SparseArray<MutableLiveData> array = sparseIntArrayMap.get(dataType);
        if (array == null) {
            array = new SparseArray<>();
            sparseIntArrayMap.put(dataType, array);
        }
        MutableLiveData<Resources<T>> liveData = array.get(flag);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            array.put(flag, liveData);
        }
        return liveData;
    }

    /**
     * Get the LiveData of given list type.
     *
     * @param dataType the data type of element in list.
     * @param <T>      the generic data type.
     * @return         the live data.
     */
    public <T> MutableLiveData<Resources<List<T>>> getListObservable(Class<T> dataType) {
        MutableLiveData<Resources<List<T>>> liveData = listLiveDataMap.get(dataType);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            listLiveDataMap.put(dataType, liveData);
        }
        return liveData;
    }

    /**
     * Get the LiveData of given list type and flag
     *
     * @param dataType the data type
     * @param flag     the flag
     * @param <T>      the generic data type.
     * @return         the live data.
     */
    public <T> MutableLiveData<Resources<T>> getListObservable(Class<T> dataType, int flag) {
        SparseArray<MutableLiveData> array = listSparseIntArrayMap.get(dataType);
        if (array == null) {
            array = new SparseArray<>();
            listSparseIntArrayMap.put(dataType, array);
        }
        MutableLiveData<Resources<T>> liveData = array.get(flag);
        if (liveData == null) {
            liveData = new MutableLiveData<>();
            array.put(flag, liveData);
        }
        return liveData;
    }

    /**
     * Called when the {@link android.support.v4.app.Fragment#onCreate(Bundle)}
     * or the {@link android.app.Activity#onCreate(Bundle)} was called.
     *
     * @param extras             extras from {@link Intent#getExtras()} of Activity, or {@link Fragment#getArguments()}.
     * @param savedInstanceState saved instance state
     */
    public void onCreate(Bundle extras, Bundle savedInstanceState) {
        // default no implementation
    }

    /**
     * Called when the {@link android.support.v4.app.Fragment#onSaveInstanceState(Bundle)}
     * or the {@link android.app.Activity#onSaveInstanceState(Bundle)} was called.
     *
     * @param outState saved instance state
     */
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // default no implementation
    }

    /**
     * Called when the {@link Activity#onActivityResult(int, int, Intent)}
     * or {@link Fragment#onActivityResult(int, int, Intent)} as called.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the intent data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // default no implementation
    }

    /**
     * Called when the {@link android.support.v4.app.Fragment#onDestroy()}
     * or the {@link Activity#onDestroy()} was called.
     */
    public void onDestroy() {
        // default no implementation
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
