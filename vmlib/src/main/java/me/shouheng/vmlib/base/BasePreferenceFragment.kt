package me.shouheng.vmlib.base

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.annotation.XmlRes
import androidx.lifecycle.*
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.stability.L
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
abstract class BasePreferenceFragment<U : BaseViewModel> : PreferenceFragmentCompat(), BaseViewModelOwner<U> {

    protected val vm: U by lazy { createViewModel() }

    private var useEventBus = false

    override fun getViewModel(): U = vm

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        if (useEventBus) Bus.get().register(this)
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
        val vmClass = (this.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments
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
            PermissionUtils.checkPermissions<BaseActivity<*>?>(
                (activity as BaseActivity<*>?)!!
                , { onGetPermission() }
                , permission)
        } else {
            L.w("Request permission failed due to the " +
                    "associated activity was not instance of CommonActivity")
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
            PermissionUtils.checkPermissions<BaseActivity<*>?>(
                (activity as BaseActivity<*>?)!!
                , { onGetPermission() }
                , *permissions)
        } else {
            L.w("Request permissions failed due to the " +
                    "associated activity was not instance of CommonActivity")
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
        }
    }
}