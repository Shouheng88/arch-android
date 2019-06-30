package me.shouheng.mvvm.base;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import me.shouheng.mvvm.data.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic implementation of common ViewModel.
 *
 * @author WngShhng 2019-6-29
 */
public class CommonViewModel extends AndroidViewModel {

    private Map<Class, MutableLiveData> liveDataMap = new HashMap<>();

    private Map<Class, MutableLiveData> listLiveDataMap = new HashMap<>();

    public CommonViewModel(@NonNull Application application) {
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
}
