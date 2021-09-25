package me.shouheng.vmlib.component;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import me.shouheng.vmlib.bean.Resources;

/**
 * One holder for {@link androidx.lifecycle.LiveData}
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2020-05-17 20:34
 */
public class LiveDataHolder<T> {

    private Map<String, SingleLiveEvent> map = new HashMap<>();

    public MutableLiveData<Resources<T>> getLiveData(Class dataType, Integer sid, boolean single) {
        String key = dataType.getName() + "_" + single;
        if (sid != null) key = key + "_" + sid;
        return getLiveDataInner(key, single);
    }

    private MutableLiveData<Resources<T>> getLiveDataInner(String key, boolean single) {
        SingleLiveEvent<Resources<T>> liveData = map.get(key);
        if (liveData == null) {
            liveData = new SingleLiveEvent<>(single);
            map.put(key, liveData);
        }
        return liveData;
    }
}
