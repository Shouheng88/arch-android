package me.shouheng.vmlib.component;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import me.shouheng.vmlib.bean.Resources;

/**
 * An intermediate livedata, none sticky.
 *
 * @Author wangshouheng
 * @Time 2022/10/3
 */
public class IntermediateLiveData extends AdvancedLiveData<IntermediateLiveData.IntermediateLiveEvent> {

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super IntermediateLiveEvent> observer) {
        super.observe(owner, false, observer);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, boolean sticky, @NonNull Observer<? super IntermediateLiveEvent> observer) {
        super.observe(owner, false, observer);
    }

    public static <T> Observer<? super IntermediateLiveEvent> createIntermediateObserver(
            @NonNull LiveData<T> liveData, @NonNull Observer<? super IntermediateLiveEvent> observer) {
        String key = acquireIntermediateLiveEventKey(liveData);
        return new IntermediateLiveEventObserver(key, observer);
    }

    public static <T> IntermediateLiveEvent createIntermediateLiveEvent(@NonNull LiveData<T> liveData, Resources data) {
        String key = acquireIntermediateLiveEventKey(liveData);
        return new IntermediateLiveEvent(key, data);
    }

    private static <T> String acquireIntermediateLiveEventKey(@NonNull LiveData<T> liveData) {
        return "livedata@" + liveData.hashCode();
    }

    public static class IntermediateLiveEvent {

        private final String targetKey;

        private final Object data;

        public IntermediateLiveEvent(String targetKey, Object data) {
            this.targetKey = targetKey;
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "IntermediateLiveEvent{" +
                    "targetKey='" + targetKey + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class IntermediateLiveEventObserver implements Observer<IntermediateLiveEvent> {

        private final String targetKey;

        private final Observer<? super IntermediateLiveEvent> observer;

        public IntermediateLiveEventObserver(String targetKey, Observer<? super IntermediateLiveEvent> observer) {
            this.targetKey = targetKey;
            this.observer = observer;
        }

        @Override
        public void onChanged(IntermediateLiveEvent intermediateLiveEvent) {
            if (targetKey.equals(intermediateLiveEvent.targetKey)) {
                observer.onChanged(intermediateLiveEvent);
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "IntermediateLiveEventObserver{" +
                    "targetKey='" + targetKey + '\'' +
                    '}';
        }
    }
}
