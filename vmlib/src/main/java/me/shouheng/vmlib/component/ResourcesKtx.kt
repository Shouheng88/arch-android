package me.shouheng.vmlib.bean

inline fun <T> Resources<T>.onSuccess(
    block: (res: Resources<T>) -> Unit
): Resources<T> = apply {
    if (isSuccess) {
        block.invoke(this)
    }
}

inline fun <T> Resources<T>.onFailure(
    block: (res: Resources<T>) -> Unit
): Resources<T> = apply {
    if (isFailure) {
        block.invoke(this)
    }
}

inline fun <T> Resources<T>.onLoading(
    block: (res: Resources<T>) -> Unit
): Resources<T> = apply {
    if (isLoading) {
        block.invoke(this)
    }
}

inline fun <T> Resources<T>.onProgress(
    block: (res: Resources<T>) -> Unit
): Resources<T> = apply {
    if (isProgress) {
        block.invoke(this)
    }
}
