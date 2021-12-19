package me.shouheng.vmlib.task

import kotlinx.coroutines.Job

/** Run a suspend task in Kotlin DSL style. */
inline fun <R> execute(
    init: TaskBuilder<R>.() -> Unit
): Job {
    val builder = TaskBuilder<R>()
    builder.init()
    return builder.launch()
}
