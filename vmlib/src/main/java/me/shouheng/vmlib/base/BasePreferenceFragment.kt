package me.shouheng.vmlib.base

import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.text.TextUtils
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.umeng.analytics.MobclickAgent
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.Platform
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.bus.Bus
import me.shouheng.vmlib.component.*
import java.lang.reflect.ParameterizedType

/**
 * base preference fragment for mvvm
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-10-02 13:15
 */
abstract class BasePreferenceFragment<U : BaseViewModel> : PreferenceFragment(), BaseViewModelOwner<U> {
    protected val vm: U by lazy { createViewModel() }
    private var useEventBus = false
    private var umengConfig: UMenuConfig? = null

    private val lifecycle = LifecycleRegistry(this)

    /**
     * The lifecycle of preference is associated with the activity.
     * So, here, we force the activity bind with this fragment is subclass of [AppCompatActivity].
     *
     * @WARN: THE ACTIVITY MIGHT BE ATTACHED WHEN THIS METHOD IS CALLED, SO YOU SHOULD AT
     * LEAST CALL THIS METHOD AFTER [onActivityCreated]. FOR EXAMPLE, CALLING [LiveData.observe]
     * METHOD, WHICH CALL THIS METHOD INDIRECTLY.
     */
    override fun getLifecycle(): Lifecycle = lifecycle

    override fun getViewModel(): U = vm

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useEventBus) Bus.get().register(this)
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

    @XmlRes protected abstract fun getPreferencesResId(): Int

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
        return ViewModelProviders.of((activity as androidx.fragment.app.FragmentActivity))[vmClass]
    }

    /**
     * Check single permission. For multiple permissions at the same time, call [checkPermissions].
     *
     * @param onGetPermission the callback when got all permissions required.
     * @param permission the permissions to request.
     */
    protected fun checkPermission(@Permission permission: Int, onGetPermission: () -> Unit) {
        if (activity is BaseActivity<*>) {
            PermissionUtils.checkPermissions<BaseActivity<*>?>((activity as BaseActivity<*>?)!!, { onGetPermission() }, permission)
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
    protected fun checkPermissions(onGetPermission: () -> Unit, @Permission vararg permissions: Int) {
        if (activity is BaseActivity<*>) {
            PermissionUtils.checkPermissions<BaseActivity<*>?>((activity as BaseActivity<*>?)!!, { onGetPermission() }, *permissions)
        } else {
            L.w("Request permissions failed due to the associated activity was not instance of CommonActivity")
        }
    }

    /**
     * Find preference from string resource key.
     *
     * @param keyRes preference key resources
     * @return       preference
     */
    protected fun <T : Preference> f(@StringRes keyRes: Int): T? {
        return findPreference(getString(keyRes)) as? T
    }

    protected fun <T : Preference> f(key: CharSequence): T? {
        return findPreference(key) as? T
    }

    /** Get support fragment manager  */
    protected fun sfm(): androidx.fragment.app.FragmentManager? {
        return if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).supportFragmentManager
        } else null
    }

    override fun onResume() {
        super.onResume()
        if (Platform.DEPENDENCY_UMENG_ANALYTICS
            && umengConfig != null
            && umengConfig?.manual == false) {
            MobclickAgent.onPageStart(umengConfig?.pageName?:"")
        }
    }

    override fun onPause() {
        super.onPause()
        if (Platform.DEPENDENCY_UMENG_ANALYTICS
            && umengConfig != null
            && umengConfig?.manual == false) {
            MobclickAgent.onPageEnd(umengConfig?.pageName?:"")
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
            umengConfig = UMenuConfig(
                if (TextUtils.isEmpty(configuration.umeng.pageName))
                    javaClass.simpleName else configuration.umeng.pageName,
                false,
                configuration.umeng.useUmengManual
            )
        }
    }
}