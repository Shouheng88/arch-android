package me.shouheng.mvvm.base;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import me.shouheng.mvvm.bean.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic implementation of common ViewModel.
 *
 * The view model has no pre-defined model associated, for MVVMs don't want to take care of the
 * model logic. You can get the data source anywhere, which is perhaps useful for small project.
 *
 * @author WngShhng 2019-6-29
 */
public class BaseViewModel extends AndroidViewModel {

    private Map<Class, MutableLiveData> liveDataMap = new HashMap<>();

    private Map<Class, MutableLiveData> listLiveDataMap = new HashMap<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Get the LiveData of given type.
     *
     * @param dataType the data type.
     * @param <T> the data type.
     * @return the live data.
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
     * Get live data of given list type.
     *
     * @param dataType the data type of element in list.
     * @param <T> the generic data type.
     * @return the live data.
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
     * Called when the {@link android.support.v4.app.Fragment#onCreate(Bundle)}
     * or the {@link android.app.Activity#onCreate(Bundle)} was called.
     *
     * @param extras extras from {@link Intent#getExtras()} of Activity, or {@link Fragment#getArguments()}.
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
     * Called when the {@link android.support.v4.app.Fragment#onDestroy()}
     * or the {@link Activity#onDestroy()} was called.
     */
    public void onDestroy() {
        // default no implementation
    }
}
