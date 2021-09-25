package me.shouheng.vmlib.task

/** Run a none-suspend task in Kotlin DSL style. */
fun <R> execute(
    init: TaskBuilder<R>.() -> Unit
) {
    val builder = TaskBuilder<R>()
    builder.init()
    builder.launch()
}

/** Run a suspend task in Kotlin DSL style. */
fun <R> executeSuspend(
    init: SuspendTaskBuilder<R>.() -> Unit
) {
    val builder = SuspendTaskBuilder<R>()
    builder.init()
    builder.launch()
}
