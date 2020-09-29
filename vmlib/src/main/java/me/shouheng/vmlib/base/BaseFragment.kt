package me.shouheng.vmlib.base

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umeng.analytics.MobclickAgent
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
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
 * The base common fragment implementation for MVVMs. Example:
 *
 * @author [WngShhng](mailto:shouheng2015@gmail.com)
 * @version 2019-6-29
 */
abstract class BaseFragment<U : BaseViewModel> : Fragment() {
    protected lateinit var vm: U
        private set
    private var shareViewModel = false
    private var useEventBus = false

    /** Grouped values with [FragmentConfiguration.umeng].  */
    private var useUmengManual = false
    private var pageName: String? = null

    /**
     * Get the layout resource id from subclass.
     *
     * @return layout resource id.
     */
    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    /**
     * Do create view business.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected abstract fun doCreateView(savedInstanceState: Bundle?)

    /**
     * Initialize view model according to the generic class type. Override this method to
     * add your owen implementation.
     *
     * Add [FragmentConfiguration] to the subclass and set
     * [FragmentConfiguration.shareViewModel] true
     * if you want to share view model between several fragments.
     *
     * @return the view model instance.
     */
    protected fun createViewModel(): U {
        val vmClass: Class<U> = (this.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<U>
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
                              flag: Int? = null,
                              success: (res: Resources<T>) -> Unit = {},
                              fail: (res: Resources<T>) -> Unit = {},
                              loading: (res: Resources<T>) -> Unit = {}) {
        observe(dataType, flag, false, success, fail, loading)
    }

    /** Observe data */
    protected fun <T> observe(dataType: Class<T>,
                              flag: Int? = null,
                              single: Boolean = false,
                              success: (res: Resources<T>) -> Unit = {},
                              fail: (res: Resources<T>) -> Unit = {},
                              loading: (res: Resources<T>) -> Unit = {}) {
        vm.getObservable(dataType, flag, single).observe(this, Observer { res ->
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
                                  flag: Int? = null,
                                  success: (res: Resources<List<T>>) -> Unit = {},
                                  fail: (res: Resources<List<T>>) -> Unit = {},
                                  loading: (res: Resources<List<T>>) -> Unit = {}) {
        observeList(dataType, flag, false, success, fail, loading)
    }

    /** Observe list data */
    protected fun <T> observeList(dataType: Class<T>,
                                  flag: Int? = null,
                                  single: Boolean = false,
                                  success: (res: Resources<List<T>>) -> Unit = {},
                                  fail: (res: Resources<List<T>>) -> Unit = {},
                                  loading: (res: Resources<List<T>>) -> Unit = {}) {
        vm.getListObservable(dataType, flag, single).observe(this, Observer { res ->
            when (res?.status) {
                Status.SUCCESS -> success(res)
                Status.LOADING -> loading(res)
                Status.FAILED -> fail(res)
            }
        })
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

    /** Start given activity.*/
    protected fun startActivity(clz: Class<out Activity?>) {
        ActivityUtils.start(context!!, clz)
    }

    /** Start given activity.*/
    protected fun startActivity(activityClass: Class<out Activity?>, requestCode: Int) {
        ActivityUtils.start(this, activityClass, requestCode)
    }

    /** Start given activity.*/
    protected fun startActivity(activityClass: Class<out Activity?>, requestCode: Int, @ActivityDirection direction: Int) {
        ActivityUtils.start(this, activityClass, requestCode, direction)
    }

    /**
     * Check single permission. For multiple permissions at the same time, call
     * [.checkPermissions].
     *
     * @param permission the permission to check
     * @param onGetPermissionCallback the callback when got the required permission
     */
    protected fun checkPermission(@Permission permission: Int, onGetPermissionCallback: OnGetPermissionCallback?) {
        if (activity is CommonActivity<*, *>) {
            PermissionUtils.checkPermissions<CommonActivity<*, *>?>((
                    activity as CommonActivity<*, *>?)!!, onGetPermissionCallback, permission)
        } else {
            L.w("Request permission failed due to the associated activity was not instance of CommonActivity")
        }
    }

    /**
     * Check multiple permissions at the same time.
     *
     * @param onGetPermissionCallback the callback when got all permissions required.
     * @param permissions the permissions to request.
     */
    protected fun checkPermissions(onGetPermissionCallback: OnGetPermissionCallback?, @Permission vararg permissions: Int) {
        if (activity is CommonActivity<*, *>) {
            PermissionUtils.checkPermissions<CommonActivity<*, *>?>(
                (activity as CommonActivity<*, *>?)!!, onGetPermissionCallback, *permissions)
        } else {
            L.w("Request permissions failed due to the associated activity was not instance of CommonActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useEventBus) Bus.get().register(this)
        vm = createViewModel()
        super.onCreate(savedInstanceState)
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