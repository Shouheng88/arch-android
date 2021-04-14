package me.shouheng.vmlib.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.annotation.StringRes
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.umeng.analytics.MobclickAgent
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.permission.callback.OnGetPermissionCallback
import me.shouheng.utils.stability.L
import me.shouheng.utils.ui.ToastUtils
import me.shouheng.vmlib.Platform
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status
import me.shouheng.vmlib.bus.Bus
import java.lang.reflect.ParameterizedType

/**
 * base preference fragment for mvvm
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-10-02 13:15
 */
abstract class BasePreferenceFragment<U : BaseViewModel> : PreferenceFragment() {
    protected lateinit var vm: U
        private set
    private var useEventBus = false

    /** Grouped values with [FragmentConfiguration.umeng].  */
    private var pageName: String? = null
    private var useUmengManual = false

    /** See document of [BaseFragment.results]. */
    private val results: MutableList<Triple<Int, Boolean, (code: Int, data: Intent?)->Unit>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useEventBus) {
            Bus.get().register(this)
        }
        vm = createViewModel()
        super.onCreate(savedInstanceState)
        val preferencesResId = getPreferencesResId()
        require(preferencesResId > 0) { "The subclass must provider a valid preference resources id." }
        addPreferencesFromResource(preferencesResId)
        doCreateView(savedInstanceState)
    }

    /**
     * Do create view business.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected abstract fun doCreateView(savedInstanceState: Bundle?)

    protected abstract fun getPreferencesResId(): Int

    /**
     * Initialize view model according to the generic class type. Override this method to
     * add your owen implementation.
     *
     * Add [FragmentConfiguration] to the subclass and set [FragmentConfiguration.shareViewModel] true
     * if you want to share view model between several fragments.
     *
     * @return the view model instance.
     */
    protected fun createViewModel(): U {
        val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            .firstOrNull { ViewModel::class.java.isAssignableFrom(it as Class<*>) } as? Class<U>
            ?: throw IllegalStateException("You must specify a view model class.")
        return ViewModelProviders.of((activity as FragmentActivity))[vmClass]
    }

    /** Observe data */
    protected fun <T> observe(dataType: Class<T>,
                              success: (res: Resources<T>) -> Unit = {},
                              fail: (res: Resources<T>) -> Unit = {},
                              loading: (res: Resources<T>) -> Unit = {}) {
        observe(dataType, null, false, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(dataType: Class<T>,
                              single: Boolean = false,
                              success: (res: Resources<T>) -> Unit = {},
                              fail: (res: Resources<T>) -> Unit = {},
                              loading: (res: Resources<T>) -> Unit = {}) {
        observe(dataType, null, single, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(dataType: Class<T>,
                              sid: Int? = null,
                              success: (res: Resources<T>) -> Unit = {},
                              fail: (res: Resources<T>) -> Unit = {},
                              loading: (res: Resources<T>) -> Unit = {}) {
        observe(dataType, sid, false, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(dataType: Class<T>,
                              sid: Int? = null,
                              single: Boolean = false,
                              success: (res: Resources<T>) -> Unit = {},
                              fail: (res: Resources<T>) -> Unit = {},
                              loading: (res: Resources<T>) -> Unit = {}) {
        vm.getObservable(dataType, sid, single).observe(activity as AppCompatActivity, Observer { res ->
            when (res?.status) {
                Status.SUCCESS -> success(res)
                Status.LOADING -> loading(res)
                Status.FAILED -> fail(res)
            }
        })
    }

    /** Observe list data */
    protected fun <T> observeList(dataType: Class<T>,
                                  success: (res: Resources<List<T>>) -> Unit = {},
                                  fail: (res: Resources<List<T>>) -> Unit = {},
                                  loading: (res: Resources<List<T>>) -> Unit = {}) {
        observeList(dataType, null, false, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(dataType: Class<T>,
                                  single: Boolean = false,
                                  success: (res: Resources<List<T>>) -> Unit = {},
                                  fail: (res: Resources<List<T>>) -> Unit = {},
                                  loading: (res: Resources<List<T>>) -> Unit = {}) {
        observeList(dataType, null, single, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(dataType: Class<T>,
                                  sid: Int? = null,
                                  success: (res: Resources<List<T>>) -> Unit = {},
                                  fail: (res: Resources<List<T>>) -> Unit = {},
                                  loading: (res: Resources<List<T>>) -> Unit = {}) {
        observeList(dataType, sid, false, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(dataType: Class<T>,
                                  sid: Int? = null,
                                  single: Boolean = false,
                                  success: (res: Resources<List<T>>) -> Unit = {},
                                  fail: (res: Resources<List<T>>) -> Unit = {},
                                  loading: (res: Resources<List<T>>) -> Unit = {}) {
        vm.getListObservable(dataType, sid, single).observe(activity as AppCompatActivity, Observer { res ->
            when (res?.status) {
                Status.SUCCESS -> success(res)
                Status.LOADING -> loading(res)
                Status.FAILED -> fail(res)
            }
        })
    }

    /**
     * Make a simple toast.
     *
     * @param text the content to display
     */
    protected fun toast(text: CharSequence?) {
        ToastUtils.showShort(text)
    }

    protected fun toast(@StringRes resId: Int) {
        ToastUtils.showShort(resId)
    }

    /**
     * Post one event by Bus
     *
     * @param event the event to post
     */
    protected fun post(event: Any?) {
        Bus.get().post(event)
    }

    /**
     * Post one sticky event by Bus
     *
     * @param event the sticky event
     */
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
    protected fun start(intent: Intent, request: Int, callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }) {
        results.add(Triple(request, true, callback))
        super.startActivityForResult(intent, request)
    }

    /** @see BaseActivity.start */
    protected fun start(intent: Intent, request: Int, options: Bundle?, callback: (code: Int, data: Intent?) -> Unit = { _, _ -> }) {
        results.add(Triple(request, true, callback))
        super.startActivityForResult(intent, request, options)
    }

    /** @see BaseActivity.start */
    protected fun onResult(request: Int, callback: (code: Int, data: Intent?) -> Unit) {
        results.add(Triple(request, false, callback))
    }

    /** @see BaseActivity.onResult */
    protected fun onResult(request: Int, single: Boolean, callback: (code: Int, data: Intent?) -> Unit) {
        results.add(Triple(request, single, callback))
    }

    /**
     * Find preference from string resource key.
     *
     * @param keyRes preference key resources
     * @return       preference
     */
    protected fun <T : Preference> f(@StringRes keyRes: Int): T {
        return findPreference(getString(keyRes)) as T
    }

    protected fun <T : Preference> f(key: CharSequence): T {
        return findPreference(key) as T
    }

    /** Get support fragment manager  */
    protected fun sfm(): FragmentManager? {
        return if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportFragmentManager
        } else null
    }

    override fun onResume() {
        super.onResume()
        if (useUmengManual && Platform.DEPENDENCY_UMENG_ANALYTICS) {
            MobclickAgent.onPageStart(pageName)
        }
    }

    override fun onPause() {
        super.onPause()
        if (useUmengManual && Platform.DEPENDENCY_UMENG_ANALYTICS) {
            MobclickAgent.onPageEnd(pageName)
        }
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

    override fun onDestroy() {
        if (useEventBus) {
            Bus.get().unregister(this)
        }
        super.onDestroy()
    }

    init {
        val configuration = this.javaClass.getAnnotation(FragmentConfiguration::class.java)
        if (configuration != null) {
            useEventBus = configuration.useEventBus
            val umengConfiguration = configuration.umeng
            pageName = if (TextUtils.isEmpty(umengConfiguration.pageName))
                javaClass.simpleName else umengConfiguration.pageName
            useUmengManual = umengConfiguration.useUmengManual
        }
    }
}