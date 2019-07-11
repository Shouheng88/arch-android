package me.shouheng.mvvm.base;

import android.app.Service;
import me.shouheng.mvvm.base.anno.ServiceConfiguration;
import me.shouheng.mvvm.bus.Bus;

/**
 * Base service to handle {@link ServiceConfiguration} annotation.
 *
 * @author WngShhng 2019-7-1
 */
public abstract class BaseService extends Service {

    private boolean useEventBus = false;

    public BaseService() {
        super();
        ServiceConfiguration configuration = this.getClass().getAnnotation(ServiceConfiguration.class);
        if (configuration != null) {
            useEventBus = configuration.useEventBus();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (useEventBus) {
            Bus.get().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus) {
            Bus.get().unregister(this);
        }
    }
}
