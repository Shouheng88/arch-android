package me.shouheng.vmlib.exception

/**
 * Global exception manager.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
object GlobalExceptionManager {

    private val handlers = mutableListOf<ExceptionHandler>()

    /** Register exception handler. */
    fun registerExceptionHandler(handler: ExceptionHandler) {
        if (!handlers.contains(handler)) handlers.add(handler)
    }

    /** Un-register exception handler. */
    fun unregisterExceptionHandler(handler: ExceptionHandler) {
        handlers.remove(handler)
    }

    /** Handle exception. */
    fun handleException(t: Throwable): Pair<String, String> {
        handlers.forEach {
            val result = it.handleException(t)
            if (result != null) {
                return result
            }
        }
        return when(t) {
            is NoNetworkException -> "-1" to "Please check your network"
            else -> "-1" to (t.message?:"")
        }
    }
}

/** The exception handler. */
interface ExceptionHandler {

    /**
     * Handle given exception. Return the error code and message by [Pair] or
     * return null if you don't want to handle given exception. At this time,
     * the library will use the default implementation.
     */
    fun handleException(t: Throwable): Pair<String, String>?
}
