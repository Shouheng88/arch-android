package me.shouheng.mvvm.base;

import android.app.IntentService;
import me.shouheng.mvvm.base.anno.ServiceConfiguration;
import me.shouheng.mvvm.bus.EventBusManager;

/**
 * Base intent service implementation to handle {@link ServiceConfiguration} annotation.
 *
 * @author WngShhng 2019-7-01
 */
public abstract class BaseIntentService extends IntentService {

    private boolean useEventBus = false;

    public BaseIntentService(String name) {
        super(name);
        ServiceConfiguration configuration = this.getClass().getAnnotation(ServiceConfiguration.class);
        if (configuration != null) {
            useEventBus = configuration.useEventBus();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (useEventBus) {
            EventBusManager.getInstance().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus) {
            EventBusManager.getInstance().unregister(this);
        }
    }
}
