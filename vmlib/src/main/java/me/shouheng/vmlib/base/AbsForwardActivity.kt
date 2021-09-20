package me.shouheng.vmlib.base

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MenuRes
import me.shouheng.vmlib.component.ResultCallbackDefinition

/**
 * Call [startActivityForResult] in a forward activity.
 *
 * @Author wangshouheng
 * @Time 2021/9/15
 */
abstract class AbsForwardActivity<U : BaseViewModel>: BaseActivity<U>() {

    @MenuRes private var menuResId: Int = -1

    private val results: MutableList<ResultCallbackDefinition> = mutableListOf()

    /** Menu options item selected callback */
    private var onOptionsItemSelectedCallback: ((item: MenuItem) -> Unit)? = null

    /** On activity pressed callback */
    private var onBackCallback: ((back: () -> Unit) -> Unit)? = null

    /**
     * Start activity and add the result callback for [requestCode].
     *
     * [requestCode] the request code of [startActivityForResult]
     * [callback] the activity result event callback
     */
    protected fun start(
        intent: Intent,
        requestCode: Int,
        callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }
    ) {
        results.add(ResultCallbackDefinition(requestCode, true, callback))
        super.startActivityForResult(intent, requestCode)
    }

    /**
     * Start activity and add the result callback for [requestCode].
     *
     * [requestCode] the request code of [startActivityForResult]
     * [callback] the activity result event callback
     */
    protected fun start(
        intent: Intent,
        requestCode: Int,
        options: Bundle?,
        callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }
    ) {
        results.add(ResultCallbackDefinition(requestCode, true, callback))
        super.startActivityForResult(intent, requestCode, options)
    }

    /**
     * When get result of request code base on [onActivityResult]
     *
     * [requestCode]: the request code of [start]
     * [callback]: the activity result event callback
     */
    protected fun onResult(
        requestCode: Int,
        callback: (code: Int, data: Intent?)->Unit
    ) {
        results.add(ResultCallbackDefinition(requestCode, false, callback))
    }

    /**
     * When get result of request code base on [onActivityResult]
     *
     * [request]:  the request code of [start]
     * [single]:   Should the callback be removed once the callback was invoked
     * [callback]: the activity result event callback
     */
    protected fun onResult(
        request: Int,
        single: Boolean,
        callback: (code: Int, data: Intent?)->Unit
    ) {
        results.add(ResultCallbackDefinition(request, single, callback))
    }

    /** Set menu resources id if you want to use menu. */
    protected fun setMenu(@MenuRes menuResId: Int, callback: (item: MenuItem) -> Unit) {
        this.menuResId = menuResId
        this.onOptionsItemSelectedCallback = callback
    }

    /**
     * Activity back event callback. If you want to continue back event call back method of [callback].
     *
     * ```kotlin
     * // Call sample:
     * onBack{ back ->
     *   if (onBackPressed + 2000L > System.currentTimeMillis()) {
     *     // Call back if you continue to go back. We use the method since you are able
     *     // to call this method in any situation, especially, async situation.
           back()
     *   }
     * }
     * ```
     */
    protected fun onBack(callback: (back: () -> Unit) -> Unit) {
        this.onBackCallback = callback
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menuResId != -1) menuInflater.inflate(menuResId, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.let { onOptionsItemSelectedCallback?.invoke(it) }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val it = results.iterator()
        while (it.hasNext()) {
            val pair = it.next()
            // callback for all
            if (pair.requestCode == requestCode) {
                pair.handler.invoke(resultCode, data)
                // oneshot request
                if (pair.removeWhenCalled) {
                    it.remove()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (onBackCallback != null) {
            onBackCallback?.invoke { super.onBackPressed() }
        } else {
            super.onBackPressed()
        }
    }
}