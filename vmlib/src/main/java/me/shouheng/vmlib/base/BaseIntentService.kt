package me.shouheng.vmlib.base

import android.app.IntentService
import androidx.lifecycle.LifecycleOwner
import me.shouheng.vmlib.anno.ServiceConfiguration
import me.shouheng.vmlib.bus.Bus
import me.shouheng.vmlib.component.SimpleLifecycle

/**
 * Base intent service implementation to handle [ServiceConfiguration] annotation.
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-7-01
 */
abstract class BaseIntentService(name: String?) : IntentService(name), LifecycleOwner {

    private var useEventBus = false
    private val lifecycle = SimpleLifecycle(this)

    override fun onCreate() {
        super.onCreate()
        if (useEventBus) {
            Bus.get().register(this)
        }
        lifecycle.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus) {
            Bus.get().unregister(this)
        }
        lifecycle.onDestroy()
    }

    override fun getLifecycle() = lifecycle

    init {
        val configuration = this.javaClass.getAnnotation(
            ServiceConfiguration::class.java
        )
        if (configuration != null) {
            useEventBus = configuration.useEventBus
        }
    }
}