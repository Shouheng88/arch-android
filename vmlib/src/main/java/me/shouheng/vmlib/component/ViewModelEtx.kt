package me.shouheng.vmlib.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import me.shouheng.vmlib.base.BaseActivity
import me.shouheng.vmlib.base.BaseFragment
import me.shouheng.vmlib.base.BaseViewModel
import me.shouheng.vmlib.task.TaskBuilder

/** Get new viewmodel for fragment from its generic type. */
inline fun <reified VM : BaseViewModel> BaseFragment<VM>.newViewModel(
    shareViewModel: Boolean = false
): VM {
    return if (shareViewModel) {
        ViewModelProviders.of(activity!!)[VM::class.java]
    } else {
        ViewModelProviders.of(this)[VM::class.java]
    }
}

/** Get new viewmodel for activity from its generic type. */
inline fun <reified VM : BaseViewModel> BaseActivity<VM>.newViewModel(): VM {
    return ViewModelProviders.of(this)[VM::class.java]
}

/**
 * Execute task in viewmodel scope by default. If you use [TaskBuilder.executeOn]
 * to specify the execution context, then the default one will be replaced.
 */
inline fun <R> ViewModel.execute(
    init: TaskBuilder<R>.() -> Unit
): Job {
    val builder = TaskBuilder<R>()
    builder.executeOn(viewModelScope.coroutineContext)
    builder.init()
    return builder.launch()
}
