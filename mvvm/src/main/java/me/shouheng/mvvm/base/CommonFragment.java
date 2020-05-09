package me.shouheng.mvvm.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * <blockquote><pre>
 * @FragmentConfiguration(shareViewMode = true, useEventBus = true, layoutResId = R.layout.fragment_main)
 * class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>() {
 *
 *     private val downloadUrl = "https://dldir1.qq.com/music/clntupate/QQMusic_YQQFloatLayer.exe"
 *
 *     override fun doCreateView(savedInstanceState: Bundle?) {
 *         addSubscriptions()
 *         initViews()
 *         vm.shareValue = ResUtils.getString(R.string.sample_main_shared_value_between_fragments)
 *         L.d(vm)
 *     }
 *
 *     // ...
 * }
 * </pre>
 * </blockquote>
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public abstract class CommonFragment<U extends BaseViewModel, T extends ViewDataBinding> extends BaseFragment<U> {

    private T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = getLayoutResId();
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
        doCreateView(savedInstanceState);
        return binding.getRoot();
    }

    protected T getBinding() {
        return binding;
    }
}
