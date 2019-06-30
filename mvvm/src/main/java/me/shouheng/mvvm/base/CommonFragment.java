package me.shouheng.mvvm.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.shouheng.mvvm.annotation.ShareViewModel;

import java.lang.reflect.ParameterizedType;

/**
 * The base common fragment implementation for MVVMs.
 *
 * @author WngShhng 2019-6-29
 */
public abstract class CommonFragment<T extends ViewDataBinding, U extends CommonViewModel> extends Fragment {

    /**
     * The base view model.
     */
    private U vm;

    /**
     * The view data binding.
     */
    private T binding;

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
     * Initialize view model according to the generic class type. Override this method to
     * add your owen implementation.
     *
     * Add {@link ShareViewModel} to the subclass if you want to share view model between
     * several fragments.
     *
     * @return the view model instance.
     */
    protected U initViewModel() {
        Class<U> vmClass = ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        boolean isSharedViewModel = this.getClass().isAnnotationPresent(ShareViewModel.class);
        if (isSharedViewModel) {
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
        if (getLayoutResId() <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        vm = initViewModel();
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), null, false);
        doCreateView(savedInstanceState);
        return binding.getRoot();
    }

    protected U getVM() {
        return vm;
    }

    protected T getBinding() {
        return binding;
    }
}
