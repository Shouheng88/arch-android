package me.shouheng.vmlib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2019-6-29
 */
public abstract class CommonFragment<U extends BaseViewModel, T extends ViewDataBinding> extends BaseFragment<U> {

    private T binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        int layoutResId = getLayoutResId();
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        binding = DataBindingUtil.inflate(inflater, layoutResId, null, false);
        // fix 2020-06-27 remove #doCreateView() callback since it will be called after #onViewCreated()
        return binding.getRoot();
    }

    protected T getBinding() {
        return binding;
    }
}
