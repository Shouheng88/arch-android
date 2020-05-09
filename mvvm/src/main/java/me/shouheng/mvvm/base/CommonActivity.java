package me.shouheng.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import me.shouheng.utils.permission.PermissionResultResolver;

/**
 * The basic common implementation for MMVMs activity.
 *
 * Example:
 * <blockquote><pre>
 * @ActivityConfiguration(
 *     useEventBus = false,
 *     layoutResId = R.layout.activity_main,
 *     statusBarConfiguration = StatusBarConfiguration(
 *         statusBarMode = StatusBarMode.LIGHT,
 *         statusBarColor = 0xdddddd
 *     )
 * )
 * class MainActivity : CommonActivity<ActivityMainBinding, MainViewModel>() {
 *
 *     override fun doCreateView(savedInstanceState: Bundle?) {
 *         addSubscriptions()
 *         initViews()
 *         vm.startLoad()
 *     }
 *
 *     private fun addSubscriptions() {
 *         vm.getObservable(String::class.java).observe(this, Observer {
 *             when(it!!.status) {
 *                 Status.SUCCESS -> { ToastUtils.showShort(it.data) }
 *                 Status.FAILED -> { ToastUtils.showShort(it.message) }
 *                 Status.LOADING -> { }
 *                 else -> {}
 *             }
 *         })
 *     }
 *
 *     private fun initViews() {
 *         val fragment = MainFragment()
 *         supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
 *         setSupportActionBar(binding.toolbar)
 *     }
 *
 *     @Subscribe
 *     fun onGetMessage(simpleEvent: SimpleEvent) {
 *         toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
 *     }
 * }
 * </pre>
 * </blockquote>
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public abstract class CommonActivity<U extends BaseViewModel, T extends ViewDataBinding>
        extends BaseActivity<U> implements PermissionResultResolver  {

    private T binding;

    @Override
    protected void setupContentView(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutResId(), null, false);
        setContentView(binding.getRoot());
    }

    protected T getBinding() {
        return binding;
    }
}
