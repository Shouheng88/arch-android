package me.shouheng.vmlib.component;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

import me.shouheng.utils.stability.L;

/**
 * One wrapper for single event live data: only one receiver could got the live data.
 *
 * @param <T> the data type
 */
public class SingleLiveEventWrapper<T> extends AdvancedLiveData<T> {

    private final AtomicBoolean pending = new AtomicBoolean(false);

    private final AdvancedLiveData<T> liveData;

    public SingleLiveEventWrapper(AdvancedLiveData<T> liveData) {
        this.liveData = liveData;
    }

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull final Observer<? super T> observer) {
        this.observe(owner, true, observer);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, boolean sticky, @NonNull Observer<? super T> observer) {
        if (hasActiveObservers()) {
            L.d("Multiple observers registered but only one will be notified of changes.");
        }
        liveData.observe(owner, value -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value);
            }
        });
    }

    @Override
    public void observeForever(@NonNull Observer<? super T> observer) {
        if (hasActiveObservers()) {
            L.d("Multiple observers registered but only one will be notified of changes.");
        }
        liveData.observeForever(value -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(value);
            }
        });
    }

    @MainThread
    @Override
    public void setValue(@Nullable T t) {
        pending.set(true);
        liveData.setValue(t);
    }
}
