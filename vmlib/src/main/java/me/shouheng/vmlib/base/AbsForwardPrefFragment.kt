package me.shouheng.vmlib.base

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import me.shouheng.vmlib.component.ResultCallbackDefinition

/**
 * Call [startActivityForResult] directly.
 *
 * @Author wangshouheng
 * @Time 2021/9/15
 */
abstract class AbsForwardPrefFragment<U : BaseViewModel> : BasePreferenceFragment<U>() {

    @MenuRes private var menuResId: Int = -1

    /** See document of [BaseFragment.results]. */
    private val results: MutableList<ResultCallbackDefinition> = mutableListOf()

    /** Menu options item selected callback */
    private var onOptionsItemSelectedCallback: ((item: MenuItem) -> Unit)? = null

    protected fun start(
        intent: Intent,
        request: Int,
        callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }
    ) {
        results.add(ResultCallbackDefinition(request, true, callback))
        super.startActivityForResult(intent, request)
    }

    protected fun start(
        intent: Intent,
        request: Int,
        options: Bundle?,
        callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }
    ) {
        results.add(ResultCallbackDefinition(request, true, callback))
        super.startActivityForResult(intent, request, options)
    }

    protected fun onResult(
        request: Int,
        callback: (code: Int, data: Intent?) -> Unit
    ) {
        results.add(ResultCallbackDefinition(request, false, callback))
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (menuResId != -1) setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menuResId != -1) inflater.inflate(menuResId, menu)
        super.onCreateOptionsMenu(menu, inflater)
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
                if (pair.removeWhenCalled) it.remove()
            }
        }
    }
}