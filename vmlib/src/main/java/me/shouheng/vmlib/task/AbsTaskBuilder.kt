package me.shouheng.vmlib.task

import kotlinx.coroutines.Dispatchers
import me.shouheng.vmlib.bean.Resources
import kotlin.coroutines.CoroutineContext

/** Abstract task builder based on Kotlin DSL. */
@TaskDSLMaker
abstract class AbsTaskBuilder<R> {

    protected var onSucceed: ((Resources<R>) -> Unit)? = null
    protected var onLoading: ((Resources<R>) -> Unit)? = null
    protected var onFailed:  ((Resources<R>) -> Unit)? = null

    protected var launchContext: CoroutineContext  = Dispatchers.Main
    protected var executeContext: CoroutineContext = Dispatchers.IO

    /** Specify which context to launch the task. */
    fun launchOn(context: CoroutineContext) {
        this.launchContext = context
    }

    /** Specify which context to execute the task. */
    fun executeOn(context: CoroutineContext) {
        this.executeContext = context
    }

    /** Called when the task is succeed. Called in scope of [launchContext]. */
    fun onSucceed(block: (Resources<R>) -> Unit) {
        onSucceed = block
    }

    /** Called when the task is loading. Called in scope of [launchContext]. */
    fun onLoading(block: (Resources<R>) -> Unit) {
        onLoading = block
    }

    /** Called when the task is failed. Called in scope of [launchContext]. */
    fun onFailed(block: (Resources<R>) -> Unit) {
        onFailed = block
    }

    internal abstract fun launch()
}
