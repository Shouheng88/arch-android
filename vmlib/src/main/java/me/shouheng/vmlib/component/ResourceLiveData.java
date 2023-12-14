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
        Observer<? super IntermediateLiveData.IntermediateLiveEvent> intermediateLiveEventObserver =
                IntermediateLiveData.createIntermediateObserver(this, intermediateLiveEvent -> {
                    Resources<T> data;
                    if (intermediateLiveEvent != null
                            && (data = (Resources<T>) intermediateLiveEvent.getData()) != null) {
                        observer.onChanged(data);
                    }
                });
        intermediateLiveData.observe(owner, intermediateLiveEventObserver);
    }

    /**
     * Will re-dispatch events according to each status.
     *
     * @param resources resources data
     */
    @Override
    public void setValue(@Nullable Resources<T> resources) {
        if (resources == null || resources.isSuccess()) {
            // 将中间 livedata 置空，意味着清理之前的消息，这是为了防止消息乱序，比如，当页面处于后台的时候，
            // 该 livedata 先后获取到了两个类型的事件，一个 loading 和一个 success，那么，此时当页面回来
            // 的时候，可能会收到这两个事件，因为它们是两个 livedata，并且此时无法保证两个 livedata 发送的
            // 顺序，因此，在这种情况下，直接将中间过程的 livedata 置为空
            intermediateLiveData.setValue(null);
            super.setValue(resources);
        } else  {
            IntermediateLiveData.IntermediateLiveEvent event =
                    IntermediateLiveData.createIntermediateLiveEvent(this, resources);
            intermediateLiveData.setValue(event);
        }
    }
}
