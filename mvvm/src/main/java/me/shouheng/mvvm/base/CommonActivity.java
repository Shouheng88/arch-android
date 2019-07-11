package me.shouheng.mvvm.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import me.shouheng.mvvm.base.anno.ActivityConfiguration;
import me.shouheng.mvvm.bus.Bus;
import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.permission.Permission;
import me.shouheng.utils.permission.PermissionResultHandler;
import me.shouheng.utils.permission.PermissionResultResolver;
import me.shouheng.utils.permission.PermissionUtils;
import me.shouheng.utils.permission.callback.OnGetPermissionCallback;
import me.shouheng.utils.permission.callback.PermissionResultCallback;
import me.shouheng.utils.permission.callback.PermissionResultCallbackImpl;
import me.shouheng.utils.ui.ToastUtils;

import java.lang.reflect.ParameterizedType;

/**
 * The basic common implementation for MMVMs activity.
 *
 * @author WngShhng 2019-6-29
 */
public abstract class CommonActivity<T extends ViewDataBinding, VM extends BaseViewModel>
        extends AppCompatActivity
        implements PermissionResultResolver
{

    private VM vm;

    private T binding;

    private boolean useEventBus = false;

    private boolean needLogin = true;

    private OnGetPermissionCallback onGetPermissionCallback;

    {
        ActivityConfiguration configuration = this.getClass().getAnnotation(ActivityConfiguration.class);
        if (configuration != null) {
            useEventBus = configuration.useEventBus();
            needLogin = configuration.needLogin();
        }
    }

    /**
     * Get the layout resource id from subclass.
     *
     * @return layout resource id.
     */
    protected abstract int getLayoutResId();

    /**
     * Do create view business.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected abstract void doCreateView(@Nullable Bundle savedInstanceState);

    /**
     * Initialize view model. Override this method to add your own implementation.
     *
     * @return the view model will be used.
     */
    protected VM createViewModel() {
        Class<VM> vmClass = ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        return ViewModelProviders.of(this).get(vmClass);
    }

    /**
     * This method will be called before the {@link #setContentView(View)} was called.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected void beforeSetContentView(@Nullable Bundle savedInstanceState) {
        // default no implementation
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (useEventBus) {
            Bus.get().register(this);
        }
        super.onCreate(savedInstanceState);
        if (getLayoutResId() <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        vm = createViewModel();
        vm.onCreate(getIntent().getExtras(), savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), null, false);
        beforeSetContentView(savedInstanceState);
        setContentView(binding.getRoot());
        doCreateView(savedInstanceState);
    }

    protected VM getVM() {
        return vm;
    }

    protected T getBinding() {
        return binding;
    }

    /**
     * Get current fragnent of given resources id.
     *
     * @param resId the resources id.
     * @return the fragment.
     */
    protected Fragment getCurrentFragment(@IdRes int resId) {
        return getSupportFragmentManager().findFragmentById(resId);
    }

    /**
     * Does the user need login to enter this activity. This value was set by the
     * {@link ActivityConfiguration#needLogin()}, you can judge this value and implement your logic.
     *
     * @return true if the user need login.
     */
    protected boolean needLogin() {
        return needLogin;
    }

    /**
     * Make a simple toast.
     *
     * @param text the content to display
     */
    protected void showShort(final CharSequence text) {
        ToastUtils.showShort(text);
    }

    protected void showShort(@StringRes final int resId) {
        ToastUtils.showShort(resId);
    }

    protected void showShort(@StringRes final int resId, final Object... args) {
        ToastUtils.showShort(resId, args);
    }

    protected void showShort(final String format, final Object... args) {
        ToastUtils.showShort(format, args);
    }

    /**
     * Post one event by EventBus
     *
     * @param event the event to post
     */
    protected void post(Object event) {
        Bus.get().post(event);
    }

    /**
     * Post one sticky event by EventBus
     *
     * @param event the sticky event
     */
    protected void postSticky(Object event) {
        Bus.get().postSticky(event);
    }

    /**
     * Start given activity.
     *
     * @param clz the activity
     */
    protected void startActivity(@NonNull Class<? extends Activity> clz) {
        ActivityUtils.start(this, clz);
    }

    protected void startActivity(@NonNull Class<? extends Activity> activityClass,
                              int requestCode) {
        ActivityUtils.start(this, activityClass, requestCode);
    }

    /**
     * Check single permission. For multiple permissions at the same time, call
     * {@link #checkPermissions(OnGetPermissionCallback, int...)}.
     *
     * @param permission the permission to check
     * @param onGetPermissionCallback the callback when got the required permission
     */
    protected void checkPermission(@Permission.PermissionCode int permission, OnGetPermissionCallback onGetPermissionCallback) {
        PermissionUtils.checkPermissions(this, onGetPermissionCallback, permission);
    }

    /**
     * Check multiple permissions at the same time.
     *
     * @param onGetPermissionCallback the callback when got all permissions required.
     * @param permissions the permissions to request.
     */
    protected void checkPermissions(OnGetPermissionCallback onGetPermissionCallback, @Permission.PermissionCode int...permissions) {
        PermissionUtils.checkPermissions(this, onGetPermissionCallback, permissions);
    }

    /**
     * Get the permission check result callback, the default implementation was {@link PermissionResultCallbackImpl}.
     * Override this method to add your own implementation.
     *
     * @return the permission result callback
     */
    protected PermissionResultCallback getPermissionResultCallback() {
        return new PermissionResultCallbackImpl(this, onGetPermissionCallback);
    }

    @Override
    public void setOnGetPermissionCallback(OnGetPermissionCallback onGetPermissionCallback) {
        this.onGetPermissionCallback = onGetPermissionCallback;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionResultHandler.handlePermissionsResult(this,
                requestCode, permissions, grantResults, getPermissionResultCallback());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        vm.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (useEventBus) {
            Bus.get().unregister(this);
        }
        vm.onDestroy();
        super.onDestroy();
    }

    /**
     * This method is used to call the super {@link #onBackPressed()} instead of the
     * implementation of current activity. Since the current {@link #onBackPressed()} may be override.
     */
    public void superOnBackPressed() {
        super.onBackPressed();
    }
}
