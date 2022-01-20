package me.shouheng.vmlib.bean

inline fun <T> Resources<T>.onSuccess(
    block: (res: Resources<T>) -> Unit
): Resources<T> {
    if (isSucceed) {
        block.invoke(this)
    }
    return this
}

inline fun <T> Resources<T>.onFailed(
    block: (res: Resources<T>) -> Unit
): Resources<T> {
    if (isFailed) {
        block.invoke(this)
    }
    return this
}

inline fun <T> Resources<T>.onLoading(
    block: (res: Resources<T>) -> Unit
): Resources<T> {
    if (isLoading) {
        block.invoke(this)
    }
    return this
}

inline fun <T> Resources<T>.onProgress(
    block: (res: Resources<T>) -> Unit
): Resources<T> {
    if (isProgress) {
        block.invoke(this)
    }
    return this
}
