package me.shouheng.vmlib.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.umeng.analytics.MobclickAgent
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.permission.PermissionResultHandler
import me.shouheng.utils.permission.PermissionResultResolver
import me.shouheng.utils.permission.callback.OnGetPermissionCallback
import me.shouheng.utils.permission.callback.PermissionResultCallback
import me.shouheng.utils.permission.callback.PermissionResultCallbackImpl
import me.shouheng.vmlib.Platform
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.bus.Bus
import me.shouheng.vmlib.component.*
import java.lang.reflect.ParameterizedType

/**
 * Base activity
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2020-05-06 21:51
 */
abstract class BaseActivity<U : BaseViewModel> : AppCompatActivity(), PermissionResultResolver, BaseViewModelOwner<U> {
    protected val vm: U by lazy { createViewModel() }
    private var useEventBus = false
    private var needLogin = true

    /** Grouped values with [ActivityConfiguration].*/
    private var umengConfig: UMenuConfig? = null
    private var exitDirection = ActivityDirection.ANIMATE_NONE
    private var onGetPermissionCallback: OnGetPermissionCallback? = null

    /** Do create view business. */
    protected abstract fun doCreateView(savedInstanceState: Bundle?)

    /** Get the layout resource id from subclass. */
    @LayoutRes protected abstract fun getLayoutResId(): Int

    override fun getViewModel(): U = vm

    /** This method will be called before the [.setContentView] was called. */
    protected open fun setupContentView(savedInstanceState: Bundle?) {
        setContentView(getLayoutResId())
    }

    /**
     * Initialize view model from generic type of current activity.
     * This method will visit all generic types of current activity and choose the FIRST ONE
     * that assigned from [ViewModel].
     *
     * Override this method to add your own implementation.
     */
    protected fun createViewModel(): U {
        val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            .firstOrNull { ViewModel::class.java.isAssignableFrom(it as Class<*>) } as? Class<U>
            ?: throw IllegalStateException("You must specify a view model class.")
        return ViewModelProviders.of(this)[vmClass]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useEventBus) Bus.get().register(this)
        super.onCreate(savedInstanceState)
        setupContentView(savedInstanceState)
        doCreateView(savedInstanceState)
    }

    /** Get fragment of given resources id. */
    protected fun getFragment(@IdRes resId: Int): Fragment? {
        return supportFragmentManager.findFragmentById(resId)
    }

    /**
     * Does the user need login to enter this activity. This value was set by the
     * [ActivityConfiguration.needLogin], you can judge this value and implement your logic.
     *
     * @return true if the user need login.
     */
    protected fun needLogin(): Boolean = needLogin

    /**
     * Correspond to fragment's [Fragment.getContext]
     *
     * @return context
     */
    protected val context: Context
        get() = this

    /**
     * Correspond to fragment's [Fragment.getActivity]
     *
     * @return activity
     */
    protected val activity: Activity
        get() = this

    /**
     * Get the permission check result callback, the default implementation was [PermissionResultCallbackImpl].
     * Override this method to add your own implementation.
     *
     * @return the permission result callback
     */
    protected val permissionResultCallback: PermissionResultCallback
        get() = PermissionResultCallbackImpl(this, onGetPermissionCallback)

    override fun setOnGetPermissionCallback(onGetPermissionCallback: OnGetPermissionCallback) {
        this.onGetPermissionCallback = onGetPermissionCallback
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionResultHandler.handlePermissionsResult(this, requestCode,
            permissions, grantResults, permissionResultCallback)
    }

    override fun onResume() {
        super.onResume()
        if (Platform.DEPENDENCY_UMENG_ANALYTICS
            && umengConfig != null
            && umengConfig?.manual == false) {
            if (umengConfig?.hasFragment == true) {
                MobclickAgent.onPageStart(umengConfig?.pageName?:"")
            }
            MobclickAgent.onResume(this)
        }
    }

    override fun onPause() {
        super.onPause()
        if (Platform.DEPENDENCY_UMENG_ANALYTICS
            && umengConfig != null
            && umengConfig?.manual == false) {
            if (umengConfig?.hasFragment == true) {
                MobclickAgent.onPageEnd(umengConfig?.pageName?:"")
            }
            MobclickAgent.onPause(this)
        }
    }

    override fun onDestroy() {
        if (useEventBus) {
            Bus.get().unregister(this)
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (exitDirection != ActivityDirection.ANIMATE_NONE) {
            ActivityUtils.overridePendingTransition(this, exitDirection)
        }
    }

    /**
     * This method is used to call the super [onBackPressed] instead of the
     * implementation of current activity. Since the current [onBackPressed] may be override.
     */
    open fun superOnBackPressed() {
        super.onBackPressed()
    }

    init {
        val configuration = this.javaClass.getAnnotation(ActivityConfiguration::class.java)
        if (configuration != null) {
            useEventBus = configuration.useEventBus
            needLogin = configuration.needLogin
            exitDirection = configuration.exitDirection
            umengConfig = UMenuConfig(
                if (TextUtils.isEmpty(configuration.umeng.pageName))
                    javaClass.simpleName else configuration.umeng.pageName,
                configuration.umeng.fragmentActivity,
                configuration.umeng.useUmengManual
            )
        }
    }
}
