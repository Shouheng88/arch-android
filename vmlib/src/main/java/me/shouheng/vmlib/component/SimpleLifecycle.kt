package me.shouheng.vmlib.component

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/** One simple lifecycle. */
class SimpleLifecycle(provider: LifecycleOwner) : LifecycleRegistry(provider) {

    /** Handles the create, start, and resume lifecycle events. */
    fun onCreate() {
        handleLifecycleEvent(Event.ON_CREATE)
        handleLifecycleEvent(Event.ON_START)
        handleLifecycleEvent(Event.ON_RESUME)
    }

    /** Handles the pause, stop, and destroy lifecycle events. */
    fun onDestroy() {
        handleLifecycleEvent(Event.ON_PAUSE)
        handleLifecycleEvent(Event.ON_STOP)
        handleLifecycleEvent(Event.ON_DESTROY)
    }
}
