package me.shouheng.vmlib.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.ParameterizedType;

import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.constant.ActivityDirection;
import me.shouheng.utils.permission.Permission;
import me.shouheng.utils.permission.PermissionResultHandler;
import me.shouheng.utils.permission.PermissionResultResolver;
import me.shouheng.utils.permission.PermissionUtils;
import me.shouheng.utils.permission.callback.OnGetPermissionCallback;
import me.shouheng.utils.permission.callback.PermissionResultCallback;
import me.shouheng.utils.permission.callback.PermissionResultCallbackImpl;
import me.shouheng.utils.ui.ToastUtils;
import me.shouheng.vmlib.Platform;
import me.shouheng.vmlib.anno.ActivityConfiguration;
import me.shouheng.vmlib.anno.UmengConfiguration;
import me.shouheng.vmlib.bus.Bus;

/**
 * Base activity
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-05-06 21:51
 */
public abstract class BaseActivity<U extends BaseViewModel>
        extends AppCompatActivity
        implements PermissionResultResolver {

    private U vm;

    private boolean useEventBus = false;

    private boolean needLogin = true;

    /**
     * Grouped values with {@link ActivityConfiguration#umeng()}.
     */
    private String pageName;

    private boolean hasFragment = false;

    private boolean useUmengManual = false;

    private int layoutResId;

    private OnGetPermissionCallback onGetPermissionCallback;

    {
        ActivityConfiguration configuration = this.getClass().getAnnotation(ActivityConfiguration.class);
        if (configuration != null) {
            useEventBus = configuration.useEventBus();
            needLogin = configuration.needLogin();
            UmengConfiguration umengConfiguration = configuration.umeng();
            pageName = TextUtils.isEmpty(umengConfiguration.pageName()) ?
                    getClass().getSimpleName() : umengConfiguration.pageName();
            hasFragment = umengConfiguration.hasFragment();
            useUmengManual = umengConfiguration.useUmengManual();
        }
    }

    /**
     * Do create view business.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected abstract void doCreateView(@Nullable Bundle savedInstanceState);

    /**
     * Get the layout resource id from subclass.
     *
     * @return layout resource id.
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * This method will be called before the {@link #setContentView(View)} was called.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected void setupContentView(@Nullable Bundle savedInstanceState) {
        setContentView(layoutResId);
    }

    /**
     * Initialize view model. Override this method to add your own implementation.
     *
     * @return the view model will be used.
     */
    protected U createViewModel() {
        Class<U> vmClass = ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        return ViewModelProviders.of(this).get(vmClass);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (useEventBus) {
            Bus.get().register(this);
        }
        super.onCreate(savedInstanceState);
        layoutResId = getLayoutResId();
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        vm = createViewModel();
        setupContentView(savedInstanceState);
        doCreateView(savedInstanceState);
    }

    protected U getVM() {
        return vm;
    }

    /**
     * Get fragment of given resources id.
     *
     * @param resId the resources id.
     * @return the fragment.
     */
    protected Fragment getFragment(@IdRes int resId) {
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
    protected void toast(final CharSequence text) {
        ToastUtils.showShort(text);
    }

    protected void toast(@StringRes final int resId) {
        ToastUtils.showShort(resId);
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

    protected void startActivity(@NonNull Class<? extends Activity> activityClass, int requestCode) {
        ActivityUtils.start(this, activityClass, requestCode);
    }

    protected void startActivity(@NonNull Class<? extends Activity> activityClass, int requestCode, @ActivityDirection int direction) {
        ActivityUtils.start(this, activityClass, requestCode, direction);
    }

    /**
     * To find view by id
     *
     * @param id  id
     * @param <V> the view type
     * @return    the view
     */
    protected <V extends View> V f(@IdRes int id) {
        return findViewById(id);
    }

    /**
     * Correspond to fragment's {@link Fragment#getContext()}
     *
     * @return context
     */
    protected Context getContext() {
        return this;
    }

    /**
     * Correspond to fragment's {@link Fragment#getActivity()}
     *
     * @return activity
     */
    protected Activity getActivity() {
        return this;
    }

    /**
     * Check single permission. For multiple permissions at the same time, call
     * {@link #checkPermissions(OnGetPermissionCallback, int...)}.
     *
     * @param permission the permission to check
     * @param onGetPermissionCallback the callback when got the required permission
     */
    protected void checkPermission(@Permission int permission, OnGetPermissionCallback onGetPermissionCallback) {
        PermissionUtils.checkPermissions(this, onGetPermissionCallback, permission);
    }

    /**
     * Check multiple permissions at the same time.
     *
     * @param onGetPermissionCallback the callback when got all permissions required.
     * @param permissions the permissions to request.
     */
    protected void checkPermissions(OnGetPermissionCallback onGetPermissionCallback, @Permission int...permissions) {
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
    protected void onResume() {
        super.onResume();
        if (Platform.DEPENDENCY_UMENG_ANALYTICS && !useUmengManual) {
            if (hasFragment) {
                MobclickAgent.onPageStart(pageName);
            }
            MobclickAgent.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Platform.DEPENDENCY_UMENG_ANALYTICS && !useUmengManual) {
            if (hasFragment) {
                MobclickAgent.onPageEnd(pageName);
            }
            MobclickAgent.onPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (useEventBus) {
            Bus.get().unregister(this);
        }
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
