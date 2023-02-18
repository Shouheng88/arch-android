package me.shouheng.vmlib.component;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An advanced livedata wrapper.
 *
 * @param <T> the data type
 */
public class AdvancedLiveData<T> extends MutableLiveData<T> {

    private final static int START_VERSION = -1;

    private final AtomicInteger currentVersion = new AtomicInteger(START_VERSION);

    private final Handler internalHandler = new Handler(Looper.getMainLooper());

    /**
     * Add an observer. This method performs the same as standard
     * {@link androidx.lifecycle.LiveData#observe(LifecycleOwner, Observer)} API:
     *
     * - sticky: will receive the last value before observe.
     */
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, createObserverWrapper(observer, START_VERSION));
    }

    /**
     * Added observe method.
     *
     * @param owner the owner
     * @param sticky should the observer receiver values before observe event. True to receiver
     *               otherwise only new values after observe will be received.
     * @param observer the observer
     */
    public void observe(@NonNull LifecycleOwner owner, boolean sticky, @NonNull Observer<? super T> observer) {
        super.observe(owner, createObserverWrapper(observer, sticky ? START_VERSION : currentVersion.get()));
    }

    @Override
    public void setValue(@Nullable T t) {
        currentVersion.getAndIncrement();
        super.setValue(t);
    }

    /**
     * Post a value. Different from {@link androidx.lifecycle.LiveData#postValue(Object)},
     * this method will make sure the value always sent to observer. If you don't want the
     * feature "always arrive", call {@link #postValue(Object, boolean)} and set `alwaysArrive`
     * false.
     *
     * @param value the value to post
     */
    @Override
    public void postValue(T value) {
        postValue(value, false);
    }

    /**
     * Post a value.
     *
     * @param value the value to post
     * @param alwaysArrive the post value won't be covered by new value before sent to livedata
     *                     if true, otherwise, the post action will perform as standard livedata API.
     */
    public void postValue(T value, boolean alwaysArrive) {
        if (alwaysArrive) {
            // Add one event to main thread looper message queue, this will make sure
            // the post value won't be covered by new value before sent to livedata.
            internalHandler.post(new PostValueRunnable<T>(this, value));
        } else  {
            super.postValue(value);
        }
    }

    private class ObserverWrapper implements Observer<T> {

        private final Observer<? super T> observer;

        /** The version of value when the observer is added. */
        private final int version;

        public ObserverWrapper(@NonNull Observer<? super T> observer, int version) {
            this.observer = observer;
            this.version = version;
        }

        @Override
        public void onChanged(T t) {
            if (currentVersion.get() > version) {
                observer.onChanged(t);
            }
        }
    }

    private static final class PostValueRunnable<T> implements Runnable {

        private final AdvancedLiveData<T> liveData;

        private final T value;

        public PostValueRunnable(AdvancedLiveData<T> liveData, T value) {
            this.liveData = liveData;
            this.value = value;
        }

        @Override
        public void run() {
            liveData.setValue(value);
        }
    }

    private ObserverWrapper createObserverWrapper(@NonNull Observer<? super T> observer, int version) {
        return new ObserverWrapper(observer, version);
    }
}
