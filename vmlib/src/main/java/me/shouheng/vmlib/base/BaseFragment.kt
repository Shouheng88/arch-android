package me.shouheng.vmlib.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.view.*
import com.umeng.analytics.MobclickAgent
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.permission.callback.OnGetPermissionCallback
import me.shouheng.utils.stability.L
import me.shouheng.utils.ui.ToastUtils
import me.shouheng.vmlib.Platform
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bus.Bus
import java.lang.reflect.ParameterizedType

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * @author [WngShhng](mailto:shouheng2020@gmail.com)
 * @version 2019-6-29
 */
abstract class BaseFragment<U : BaseViewModel> : androidx.fragment.app.Fragment() {
    protected lateinit var vm: U
        private set
    private var shareViewModel = false
    private var useEventBus = false

    /** Grouped values with [FragmentConfiguration.umeng].  */
    private var useUmengManual = false
    private var pageName: String = javaClass.simpleName

    @MenuRes
    private var menuResId: Int = -1

    /** See document of [BaseFragment.results]. */
    private val results: MutableList<Triple<Int, Boolean, (code: Int, data: Intent?)->Unit>> = mutableListOf()

    /** Menu options item selected callback */
    private var onOptionsItemSelectedCallback: ((item: MenuItem) -> Unit)? = null

    /** Get the layout resource id from subclass. */
    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    /** Do create view business. */
    protected abstract fun doCreateView(savedInstanceState: Bundle?)

    /**
     * Initialize view model according to the generic class type.
     * Override this method to add your owen implementation.
     *
     * Add [FragmentConfiguration] to the subclass and set
     * [FragmentConfiguration.shareViewModel] true
     * if you want to share view model between several fragments.
     *
     * @return the view model instance.
     */
    protected fun createViewModel(): U {
        val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            .firstOrNull { ViewModel::class.java.isAssignableFrom(it as Class<*>) } as? Class<U>
            ?: throw IllegalStateException("You must specify a view model class.")
        return if (shareViewModel) {
            ViewModelProviders.of(activity!!)[vmClass]
        } else {
            ViewModelProviders.of(this)[vmClass]
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutResId = getLayoutResId()
        require(layoutResId > 0) { "The subclass must provider a valid layout resources id." }
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // fixed 2020-05-21: The callback must be called here
        // to use kotlin android extensions to avoid findViewById()
        doCreateView(savedInstanceState)
    }

    /** Observe data */
    protected fun <T> observe(
        dataType: Class<T>,
        success: (res: Resources<T>) -> Unit = {},
        fail: (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(dataType, null, false, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(
        dataType: Class<T>,
        single: Boolean = false,
        success: (res: Resources<T>) -> Unit = {},
        fail: (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(dataType, null, single, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(
        dataType: Class<T>,
        sid: Int? = null,
        success: (res: Resources<T>) -> Unit = {},
        fail: (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(dataType, sid, false, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false,
        success: (res: Resources<T>) -> Unit = {},
        fail: (res: Resources<T>) -> Unit = {},
        loading: (res: Resources<T>) -> Unit = {}
    ) {
        observe(vm.getObservable(dataType, sid, single), success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(
        dataType: Class<T>,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail: (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observeList(dataType, null, false, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(
        dataType: Class<T>,
        single: Boolean = false,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail: (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observeList(dataType, null, single, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(
        dataType: Class<T>,
        sid: Int? = null,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail: (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observeList(dataType, sid, false, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(
        dataType: Class<T>,
        sid: Int? = null,
        single: Boolean = false,
        success: (res: Resources<List<T>>) -> Unit = {},
        fail: (res: Resources<List<T>>) -> Unit = {},
        loading: (res: Resources<List<T>>) -> Unit = {}
    ) {
        observe(vm.getListObservable(dataType, sid, single), success, fail, loading)
    }

    /** Make a simple toast.*/
    protected fun toast(text: CharSequence?) {
        ToastUtils.showShort(text)
    }

    /** Make a simple toast.*/
    protected fun toast(@StringRes resId: Int) {
        ToastUtils.showShort(resId)
    }

    /** Post one event by Bus*/
    protected fun post(event: Any?) {
        Bus.get().post(event)
    }

    /** Post one sticky event by Bus*/
    protected fun postSticky(event: Any?) {
        Bus.get().postSticky(event)
    }

    /**
     * Check single permission. For multiple permissions at the same time, call [check].
     *
     * @param onGetPermission the callback when got all permissions required.
     * @param permission the permissions to request.
     */
    protected fun check(@Permission permission: Int, onGetPermission: () -> Unit) {
        if (activity is BaseActivity<*>) {
            PermissionUtils.checkPermissions<BaseActivity<*>?>(
                (activity as BaseActivity<*>?)!!,
                OnGetPermissionCallback {
                    onGetPermission()
                }, permission)
        } else {
            L.w("Request permission failed due to the associated activity was not instance of CommonActivity")
        }
    }

    /**
     * Check multiple permissions at the same time.
     *
     * @param onGetPermission the callback when got all permissions required.
     * @param permissions the permissions to request.
     */
    protected fun check(onGetPermission: () -> Unit, @Permission vararg permissions: Int) {
        if (activity is BaseActivity<*>) {
            PermissionUtils.checkPermissions<BaseActivity<*>?>(
                (activity as BaseActivity<*>?)!!,
                OnGetPermissionCallback {
                    onGetPermission()
                }, *permissions)
        } else {
            L.w("Request permissions failed due to the associated activity was not instance of CommonActivity")
        }
    }

    /** @see BaseActivity.start */
    protected fun start(
        intent: Intent,
        request: Int,
        callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }
    ) {
        results.add(Triple(request, true, callback))
        super.startActivityForResult(intent, request)
    }

    /** @see BaseActivity.start */
    protected fun start(
        intent: Intent,
        request: Int,
        options: Bundle?,
        callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }
    ) {
        results.add(Triple(request, true, callback))
        super.startActivityForResult(intent, request, options)
    }

    /** @see BaseActivity.start */
    protected fun onResult(
        request: Int,
        callback: (code: Int, data: Intent?) -> Unit
    ) {
        results.add(Triple(request, false, callback))
    }

    /** @see BaseActivity.onResult */
    protected fun onResult(
        request: Int,
        single: Boolean,
        callback: (code: Int, data: Intent?)->Unit
    ) {
        results.add(Triple(request, single, callback))
    }

    /** Set menu resources id if you want to use menu. */
    protected fun setMenu(@MenuRes menuResId: Int, callback: (item: MenuItem) -> Unit) {
        this.menuResId = menuResId
        this.onOptionsItemSelectedCallback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useEventBus) Bus.get().register(this)
        vm = createViewModel()
        super.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        if (Platform.DEPENDENCY_UMENG_ANALYTICS && !useUmengManual) {
            MobclickAgent.onPageStart(pageName)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Platform.DEPENDENCY_UMENG_ANALYTICS && !useUmengManual) {
            MobclickAgent.onPageEnd(pageName)
        }
    }

    override fun onDestroy() {
        if (useEventBus) {
            Bus.get().unregister(this)
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val it = results.iterator()
        while (it.hasNext()) {
            val pair = it.next()
            // callback for all
            if (pair.first == requestCode) {
                pair.third(resultCode, data)
                // oneshot request
                if (pair.second) it.remove()
            }
        }
    }

    init {
        val configuration = this.javaClass.getAnnotation(FragmentConfiguration::class.java)
        if (configuration != null) {
            shareViewModel = configuration.shareViewModel
            useEventBus = configuration.useEventBus
            val umengConfiguration = configuration.umeng
            pageName = if (TextUtils.isEmpty(umengConfiguration.pageName))
                javaClass.simpleName else umengConfiguration.pageName
            useUmengManual = umengConfiguration.useUmengManual
        }
    }
}