package me.shouheng.service.net

import kotlinx.coroutines.Deferred
import me.shouheng.utils.device.NetworkUtils
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.bean.Resources
import retrofit2.Call

/**
 * Error handler
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2019-10-04 10:51
 */
class Net {

    companion object {
        /** Connect to get any type of result. Will return the result wrapped by [Resources]. */
        suspend fun <T> connectResources(deferred: Deferred<T>): Resources<T> {
            if (!NetworkUtils.isConnected()) {
                return Resources.failed("-1", "Please check your network")
            }
            return try {
                Resources.success(deferred.await())
            } catch (t: Throwable) {
                return Resources.failed("12", "${t.message}")
            }
        }
    }
}
