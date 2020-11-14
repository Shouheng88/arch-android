package me.shouheng.vmlib.holder;

import android.arch.lifecycle.MutableLiveData;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

import me.shouheng.vmlib.base.SingleLiveEvent;
import me.shouheng.vmlib.bean.Resources;

/**
 * One holder for {@link android.arch.lifecycle.LiveData}
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-05-17 20:34
 */
public class LiveDataHolder<T> {

    private Map<Class, SingleLiveEvent> map = new HashMap<>();

    private Map<Class, SparseArray<SingleLiveEvent>> sidMap = new HashMap<>();

    public MutableLiveData<Resources<T>> getLiveData(Class dataType, Integer sid, boolean single) {
        if (sid == null) return getLiveDataInner(dataType, single);
        return getLiveDataInner(dataType, sid, single);
    }

    private MutableLiveData<Resources<T>> getLiveDataInner(Class dataType, boolean single) {
        SingleLiveEvent<Resources<T>> liveData = map.get(dataType);
        if (liveData == null) {
            liveData = new SingleLiveEvent<>(single);
            map.put(dataType, liveData);
        }
        return liveData;
    }

    private MutableLiveData<Resources<T>> getLiveDataInner(Class dataType, int sid, boolean single) {
        SparseArray<SingleLiveEvent> array = sidMap.get(dataType);
        if (array == null) {
            array = new SparseArray<>();
            sidMap.put(dataType, array);
        }
        SingleLiveEvent<Resources<T>> liveData = array.get(sid);
        if (liveData == null) {
            liveData = new SingleLiveEvent<>(single);
            array.put(sid, liveData);
        }
        return liveData;
    }
}
