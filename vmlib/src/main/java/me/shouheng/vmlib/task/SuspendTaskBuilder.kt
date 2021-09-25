package me.shouheng.vmlib.task

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status

/** Suspend task builder. */
@TaskDSLMaker
class SuspendTaskBuilder<R> : AbsTaskBuilder<R>() {

    private var realTask: (suspend () -> Resources<R>)? = null

    /**
     * The task to execute. Specify your suspend task here.
     * By default, the task start and end in [launchContext] and is executed
     * in [executeContext]. You can use [launchOn] and [executeOn] to specify
     * launch context and execute context.
     */
    fun doTask(task: suspend () -> Resources<R>) {
        realTask = task
    }

    override fun launch() {
        GlobalScope.launch(launchContext) {
            val result = withContext(executeContext) {
                try {
                    realTask?.invoke()
                } catch (e: Throwable) {
                    Resources.failed<R>("-1", e.message).apply {
                        throwable = e
                    }
                }
            }
            when(result?.status) {
                Status.SUCCESS -> { onSucceed?.invoke(result) }
                Status.LOADING -> { onLoading?.invoke(result) }
                Status.FAILED  -> { onFailed?.invoke(result)  }
            }
        }
    }
}
