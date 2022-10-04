package me.shouheng.vmlib.component;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import me.shouheng.vmlib.bean.Resources;

/**
 * The {@link AdvancedLiveData} for {@link Resources}. You can treat it as a dispatcher,
 * resources of {@link me.shouheng.vmlib.bean.Status#SUCCESS} will be sent to observers
 * of current livedata. Other events will be sent to {@link #intermediateLiveData}.
 *
 * @Author wangshouheng
 * @Time 2022/10/3
 */
public class ResourceLiveData<T> extends AdvancedLiveData<Resources<T>> {

    private final IntermediateLiveData intermediateLiveData;

    public ResourceLiveData(IntermediateLiveData intermediateLiveData) {
        this.intermediateLiveData = intermediateLiveData;
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super Resources<T>> observer) {
        this.observe(owner, true, observer);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, boolean sticky, @NonNull Observer<? super Resources<T>> observer) {
        super.observe(owner, sticky, observer);
        intermediateLiveData.observe(owner, IntermediateLiveData.createIntermediateObserver(this
                , intermediateLiveEvent -> observer.onChanged((Resources<T>) intermediateLiveEvent.getData())));
    }

    /**
     * Will re-dispatch events according to each status.
     *
     * @param resources resources data
     */
    @Override
    public void setValue(@Nullable Resources<T> resources) {
        if (resources == null || resources.isSuccess()) {
            super.setValue(resources);
        } else  {
            IntermediateLiveData.IntermediateLiveEvent event =
                    IntermediateLiveData.createIntermediateLiveEvent(this, resources);
            intermediateLiveData.setValue(event);
        }
    }
}
