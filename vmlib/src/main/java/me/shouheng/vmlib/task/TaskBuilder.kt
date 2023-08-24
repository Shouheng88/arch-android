package me.shouheng.vmlib.task

import kotlinx.coroutines.*
import me.shouheng.utils.device.NetworkUtils
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.exception.GlobalExceptionManager
import me.shouheng.vmlib.exception.NoNetworkException

/** Suspend task builder. */
@TaskDSLMaker class TaskBuilder<R> : AbsTaskBuilder<R>() {

    private var realTask: (suspend () -> Resources<R>)? = null

    /**
     * The task to execute. Specify your suspend task here.
     * By default, the task execute in [runContext].
     * You can use [executeOn] to specify the execute context.
     */
    fun task(body: suspend () -> R) {
        realTask = { Resources.success(body.invoke()) }
    }

    /**
     * The task to execute. Different from [body], this method will directly
     * get resources result from [body].
     */
    fun resources(body: suspend () -> Resources<R>) {
        realTask = body
    }

    /**
     * Make a network request. Different from [body] and [resources], this method
     * Will check the network state before make the real network request.
     */
    fun request(body: suspend () -> R) {
        realTask = {
            if (!NetworkUtils.isConnected()) {
                val result = GlobalExceptionManager.handleException(NoNetworkException())
                Resources.failure(result.first, result.second)
            } else {
                Resources.success(body())
            }
        }
    }

    /** Publish progress. */
    fun publishProgress(data: R) {
        GlobalScope.launch {
            notifyStateChanged(Resources.progress(data))
        }
    }

    /** Publish progress. */
    fun publishProgress(progress: Resources<R>) {
        GlobalScope.launch {
            notifyStateChanged(progress)
        }
    }

    override fun launch(): Job {
        return GlobalScope.launch(runContext) {
            notifyStateChanged(Resources.loading())
            val result = try {
                realTask?.invoke()
            } catch (e: Throwable) {
                val result = GlobalExceptionManager.handleException(e)
                Resources.failure<R>(result.first, result.second).apply {
                    throwable = e
                }
            }
            notifyStateChanged(result)
        }
    }
}
