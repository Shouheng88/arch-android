package me.shouheng.vmlib.component;

import java.util.HashMap;
import java.util.Map;

/**
 * The holder for {@link androidx.lifecycle.LiveData}
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2020-05-17 20:34
 */
public class LiveDataPool<T> {

    private final Map<String, ResourceLiveData<?>> pooledLiveData = new HashMap<>();

    /** A private lock to make sure thread safe. */
    private final Object lock = new Object();

    /**
     * Get a livedata instance of given data type.
     *
     * @param dataType data type
     * @param sid an sid of type, used to distinguish livedata of same data type
     * @return the live data.
     */
    public ResourceLiveData<T> acquireLiveData(Class dataType, Integer sid, IntermediateLiveData intermediate) {
        String key = dataType.getName();
        if (sid != null) key = key + "_" + sid;
        return acquireLiveDataInternal(key, intermediate);
    }

    /**
     * Acquire a live data instance from the pool. Thread safe.
     *
     * @param key the key to get an instance
     * @param intermediate the intermediate
     * @return the instance
     */
    private ResourceLiveData<T> acquireLiveDataInternal(String key, IntermediateLiveData intermediate) {
        ResourceLiveData<T> liveData = (ResourceLiveData<T>) pooledLiveData.get(key);
        if (liveData == null) {
            synchronized (lock) {
                liveData = (ResourceLiveData<T>) pooledLiveData.get(key);
                if (liveData == null) {
                    liveData = new ResourceLiveData<>(intermediate);
                    pooledLiveData.put(key, liveData);
                }
            }
        }
        return liveData;
    }
}
