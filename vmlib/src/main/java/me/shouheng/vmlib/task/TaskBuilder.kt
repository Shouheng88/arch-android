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
    fun task(task: suspend () -> R) {
        realTask = { Resources.success(task.invoke()) }
    }

    /**
     * The task to execute. Different from [task], this method will directly
     * get resources result from [task].
     */
    fun resources(task: suspend () -> Resources<R>) {
        realTask = task
    }

    /**
     * Make a network request. Different from [task] and [resources], this method
     * Will check the network state before make the real network request.
     */
    fun request(task: suspend () -> R) {
        realTask = {
            if (!NetworkUtils.isConnected()) {
                val result = GlobalExceptionManager.handleException(NoNetworkException())
                Resources.failure(result.first, result.second)
            } else {
                Resources.success(task())
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
