package me.shouheng.vmlib.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.ParameterizedType;

import me.shouheng.vmlib.anno.FragmentConfiguration;
import me.shouheng.vmlib.anno.UmengConfiguration;
import me.shouheng.vmlib.bus.Bus;
import me.shouheng.vmlib.Platform;
import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.constant.ActivityDirection;
import me.shouheng.utils.permission.Permission;
import me.shouheng.utils.permission.PermissionUtils;
import me.shouheng.utils.permission.callback.OnGetPermissionCallback;
import me.shouheng.utils.stability.L;
import me.shouheng.utils.ui.ToastUtils;

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * <blockquote><pre>
 * @FragmentConfiguration(shareViewModel = true)
 * class SecondFragment : BaseFragment<SharedViewModel>() {
 *
 *     override fun getLayoutResId(): Int = R.layout.fragment_second
 *
 *     override fun doCreateView(savedInstanceState: Bundle?) {
 *         L.d(vm)
 *         // Get and display shared value from MainFragment
 *         tv.text = vm.shareValue
 *         btn_post.setOnClickListener {
 *             Bus.get().post(SimpleEvent("MSG#00001"))
 *         }
 *     }
 * }
 * </pre>
 * </blockquote>
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public abstract class BaseFragment<U extends BaseViewModel> extends Fragment {

    private U vm;

    private boolean shareViewModel;

    private boolean useEventBus;

    /**
     * Grouped values with {@link FragmentConfiguration#umengConfiguration()}.
     */
    private boolean useUmengManual = false;

    private String pageName;

    {
        FragmentConfiguration configuration = this.getClass().getAnnotation(FragmentConfiguration.class);
        if (configuration != null) {
            shareViewModel = configuration.shareViewModel();
            useEventBus = configuration.useEventBus();
            UmengConfiguration umengConfiguration = configuration.umengConfiguration();
            pageName = TextUtils.isEmpty(umengConfiguration.pageName()) ?
                    getClass().getSimpleName() : umengConfiguration.pageName();
            useUmengManual = umengConfiguration.useUmengManual();
        }
    }

    /**
     * Get the layout resource id from subclass.
     *
     * @return layout resource id.
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * Do create view business.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected abstract void doCreateView(@Nullable Bundle savedInstanceState);

    /**
     * Initialize view model according to the generic class type. Override this method to
     * add your owen implementation.
     *
     * Add {@link FragmentConfiguration} to the subclass and set
     * {@link FragmentConfiguration#shareViewModel()} true
     * if you want to share view model between several fragments.
     *
     * @return the view model instance.
     */
    protected U createViewModel() {
        Class<U> vmClass = ((Class)((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
        if (shareViewModel) {
            return ViewModelProviders.of(getActivity()).get(vmClass);
        } else {
            return ViewModelProviders.of(this).get(vmClass);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int layoutResId = getLayoutResId();
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        return inflater.inflate(layoutResId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fixed 2020-05-21: The callback must be called here
        // to use kotlin android extensions to avoid findViewById()
        doCreateView(savedInstanceState);
    }

    protected U getVM() {
        return vm;
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
     * Post one event by Bus
     *
     * @param event the event to post
     */
    protected void post(Object event) {
        Bus.get().post(event);
    }

    /**
     * Post one sticky event by Bus
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
        ActivityUtils.start(getContext(), clz);
    }

    protected void startActivity(@NonNull Class<? extends Activity> activityClass, int requestCode) {
        ActivityUtils.start(this, activityClass, requestCode);
    }

    protected void startActivity(@NonNull Class<? extends Activity> activityClass, int requestCode, @ActivityDirection int direction) {
        ActivityUtils.start(this, activityClass, requestCode, direction);
    }

    /**
     * Check single permission. For multiple permissions at the same time, call
     * {@link #checkPermissions(OnGetPermissionCallback, int...)}.
     *
     * @param permission the permission to check
     * @param onGetPermissionCallback the callback when got the required permission
     */
    protected void checkPermission(@Permission int permission, OnGetPermissionCallback onGetPermissionCallback) {
        if (getActivity() instanceof CommonActivity) {
            PermissionUtils.checkPermissions((CommonActivity) getActivity(), onGetPermissionCallback, permission);
        } else {
            L.w("Request permission failed due to the associated activity was not instance of CommonActivity");
        }
    }

    /**
     * Check multiple permissions at the same time.
     *
     * @param onGetPermissionCallback the callback when got all permissions required.
     * @param permissions the permissions to request.
     */
    protected void checkPermissions(OnGetPermissionCallback onGetPermissionCallback, @Permission int...permissions) {
        if (getActivity() instanceof CommonActivity) {
            PermissionUtils.checkPermissions((CommonActivity) getActivity(), onGetPermissionCallback, permissions);
        } else {
            L.w("Request permissions failed due to the associated activity was not instance of CommonActivity");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (useEventBus) {
            Bus.get().register(this);
        }
        vm = createViewModel();
        vm.onCreate(getArguments(), savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        vm.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (useUmengManual && Platform.DEPENDENCY_UMENG_ANALYTICS) {
            MobclickAgent.onPageStart(pageName);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        vm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (useUmengManual && Platform.DEPENDENCY_UMENG_ANALYTICS) {
            MobclickAgent.onPageEnd(pageName);
        }
    }

    @Override
    public void onDestroy() {
        if (useEventBus) {
            Bus.get().unregister(this);
        }
        vm.onDestroy();
        super.onDestroy();
    }
}
