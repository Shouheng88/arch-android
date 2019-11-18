package me.shouheng.mvvm.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import me.shouheng.mvvm.base.anno.FragmentConfiguration;

/**
 * The base common fragment implementation for MVVMs. Sample:
 *
 * <code>
 * class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>() {
 *
 *     private val downloadUrl = "https://dldir1.qq.com/music/clntupate/QQMusic_YQQFloatLayer.exe"
 *
 *     override fun doCreateView(savedInstanceState: Bundle?) {
 *         addSubscriptions()
 *         initViews()
 *         vm.shareValue = ResUtils.getString(R.string.sample_main_shared_value_between_fragments)
 *         LogUtils.d(vm)
 *     }
 *
 *     // ...
 * }
 * </code>
 *
 * @author WngShhng 2019-6-29
 */
public abstract class CommonFragment<T extends ViewDataBinding, U extends BaseViewModel> extends Fragment {

    private U vm;

    private T binding;

    private boolean shareViewModel;

    private int layoutResId;

    {
        FragmentConfiguration configuration = this.getClass().getAnnotation(FragmentConfiguration.class);
        if (configuration != null) {
            shareViewModel = configuration.shareViewMode();
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
     * Add {@link FragmentConfiguration} to the subclass and set {@link FragmentConfiguration#shareViewMode()} true
     * if you want to share view model between several fragments.
     *
     * @return the view model instance.
     */
    protected U createViewModel() {
        Class<U> vmClass = ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        if (shareViewModel) {
            return ViewModelProviders.of(getActivity()).get(vmClass);
        } else {
            return ViewModelProviders.of(this).get(vmClass);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutResId() != 0) {
            layoutResId = getLayoutResId();
        }
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
        doCreateView(savedInstanceState);
        return binding.getRoot();
    }

    protected U getVM() {
        return vm;
    }

    protected T getBinding() {
        return binding;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        vm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        vm.onDestroy();
        super.onDestroy();
    }
}
