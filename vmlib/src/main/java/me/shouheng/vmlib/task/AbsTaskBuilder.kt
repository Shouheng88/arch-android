package me.shouheng.vmlib.task

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/** Abstract task builder based on Kotlin DSL. */
@TaskDSLMaker abstract class AbsTaskBuilder<R> {

    private var onSucceed: ((Resources<R>) -> Unit)? = null
    private var onLoading: ((Resources<R>) -> Unit)? = null
    private var onFailed : ((Resources<R>) -> Unit)? = null
    private var resultLiveData: MutableLiveData<Resources<R>>? = null

    protected var runContext: CoroutineContext = EmptyCoroutineContext

    /** Specify which context to run the task. */
    fun executeOn(context: CoroutineContext) {
        this.runContext = context
    }

    /** Bind result with livedata. */
    fun bind(liveData: MutableLiveData<Resources<R>>) {
        this.resultLiveData = liveData
    }

    /** Called when the task is succeed. */
    fun onSucceed(block: (Resources<R>) -> Unit) {
        onSucceed = block
    }

    /** Called when the task is loading. */
    fun onLoading(block: (Resources<R>) -> Unit) {
        onLoading = block
    }

    /** Called when the task is failed. */
    fun onFailed(block: (Resources<R>) -> Unit) {
        onFailed = block
    }

    /** Notify the state has changed, must run in main thread. */
    protected suspend fun notifyStateChanged(resources: Resources<R>?) {
        withContext(Dispatchers.Main) {
            when(resources?.status) {
                Status.SUCCESS -> { onSucceed?.invoke(resources) }
                Status.LOADING -> { onLoading?.invoke(resources) }
                Status.FAILED  -> { onFailed?.invoke(resources)  }
            }
            resultLiveData?.value = resources
        }
    }

    abstract fun launch(): Job
}
