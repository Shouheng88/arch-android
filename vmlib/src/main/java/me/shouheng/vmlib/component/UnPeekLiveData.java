package me.shouheng.vmlib.component;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The unpeek livedata, which is used to decide should receive previous value
 * before observe. See, https://github.com/KunMinX/UnPeek-LiveData
 *
 * @param <T> the data type
 */
public class UnPeekLiveData<T> extends MutableLiveData<T> {

    private final static int START_VERSION = -1;

    private final AtomicInteger currentVersion = new AtomicInteger(START_VERSION);

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, createObserverWrapper(observer, START_VERSION));
    }

    /**
     * Added observe method. The 'sticky' parameter here is used to mark the observe behavior
     * as sticky or not which differs in should receive the value before observe.
     */
    public void observe(@NonNull LifecycleOwner owner, boolean sticky, @NonNull Observer<T> observer) {
        super.observe(owner, createObserverWrapper(observer, sticky ? START_VERSION : currentVersion.get()));
    }

    @Override
    public void setValue(@Nullable T t) {
        currentVersion.getAndIncrement();
        super.setValue(t);
    }

    class ObserverWrapper implements Observer<T> {
        private final Observer<? super T> mObserver;
        private final int version;

        public ObserverWrapper(@NonNull Observer<? super T> observer, int version) {
            this.mObserver = observer;
            this.version = version;
        }

        @Override
        public void onChanged(T t) {
            if (currentVersion.get() > version) {
                mObserver.onChanged(t);
            }
        }
    }

    private ObserverWrapper createObserverWrapper(@NonNull Observer<? super T> observer, int version) {
        return new ObserverWrapper(observer, version);
    }
}
