package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.bus.Bus
import me.shouheng.vmlib.component.*
import java.lang.reflect.ParameterizedType

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-6-29
 */
abstract class BaseFragment<U : BaseViewModel> : Fragment(), BaseViewModelOwner<U> {

    protected val vm: U by lazy { createViewModel() }

    private var shareViewModel = false

    private var useEventBus = false

    /** Get the layout resource id from subclass. */
    @LayoutRes protected abstract fun getLayoutResId(): Int

    /** Do create view business. */
    protected abstract fun doCreateView(savedInstanceState: Bundle?)

    override fun getViewModel(): U = vm

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
        val vmClass = (this.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments
            .firstOrNull { ViewModel::class.java.isAssignableFrom(it as Class<*>) } as? Class<U>
            ?: throw IllegalStateException("You must specify a view model class.")
        return if (shareViewModel) {
            ViewModelProviders.of(requireActivity())[vmClass]
        } else {
            ViewModelProviders.of(this)[vmClass]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutResId = getLayoutResId()
        require(layoutResId > 0) {
            "The subclass must provider a valid layout resources id."
        }
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // fixed 2020-05-21: The callback must be called here
        // to use kotlin android extensions to avoid findViewById()
        doCreateView(savedInstanceState)
    }

    /**
     * Check single permission. For multiple permissions at the same time, call [checkPermission].
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
            L.w("Request permission failed due to " +
                    "the associated activity was not instance of CommonActivity")
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
            L.w("Request permissions failed due to " +
                    "the associated activity was not instance of CommonActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (useEventBus) {
            Bus.get().register(this)
        }
        super.onCreate(savedInstanceState)
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
        }
    }
}