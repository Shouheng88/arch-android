package me.shouheng.vmlib.network

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import me.shouheng.utils.device.NetworkUtils
import java.util.concurrent.atomic.AtomicInteger

/**
 * Global network state manager.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
object NetworkStateManager {

    private val stateLiveData = SimpleUnPeekLiveData<NetworkState>()

    private var networkStateReceiver: NetworkStateReceiver? = null

    /** Initialize this manager. */
    fun init(app: Application) {
        networkStateReceiver = NetworkStateReceiver()
        app.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    /** Remove the network state receiver. */
    fun destroy(context: Context) {
        context.applicationContext.unregisterReceiver(networkStateReceiver)
    }

    /** Observe the network state change. */
    fun observe(owner: LifecycleOwner, observer: Observer<NetworkState>) {
        stateLiveData.observe(owner, observer)
    }

    /**
     * Observe the network state change. Use the [sticky] to specify if
     * you want to receive the existed value when observe.
     */
    fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<NetworkState>) {
        stateLiveData.observe(owner, sticky, observer)
    }

    /** Notify the network state has changed. */
    internal fun notifyNetworkStateChanged(state: NetworkState) {
        if (state != stateLiveData.value) {
            stateLiveData.value = state
        }
    }

    /** The private unpeek livedata. */
    private class SimpleUnPeekLiveData<T> : MutableLiveData<T>() {

        private val currentVersion = AtomicInteger(START_VERSION)

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, createObserverWrapper(observer, START_VERSION))
        }

        fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<T>) {
            super.observe(owner, createObserverWrapper(observer,
                if (sticky) START_VERSION else currentVersion.get())
            )
        }

        override fun setValue(t: T?) {
            currentVersion.getAndIncrement()
            super.setValue(t)
        }

        inner class ObserverWrapper(
            private val mObserver: Observer<in T>,
            private val version: Int
        ) : Observer<T> {
            override fun onChanged(t: T) {
                if (currentVersion.get() > version) {
                    mObserver.onChanged(t)
                }
            }
        }

        private fun createObserverWrapper(
            observer: Observer<in T>,
            version: Int
        ): ObserverWrapper {
            return ObserverWrapper(observer, version)
        }

        companion object {
            private const val START_VERSION = -1
        }
    }

}

/** Network state receiver. */
class NetworkStateReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            NetworkStateManager.notifyNetworkStateChanged(
                NetworkState(NetworkUtils.isConnected())
            )
        }
    }
}

/** Network state data. */
data class NetworkState(val connected: Boolean) {

    override fun equals(other: Any?): Boolean {
        return other is NetworkState && other.connected == this.connected
    }

    override fun hashCode(): Int {
        return connected.hashCode()
    }
}
