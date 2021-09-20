package me.shouheng.vmlib.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * @author <a href="mailto:shouheng2020@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public abstract class CommonFragment<U extends BaseViewModel, T extends ViewDataBinding> extends AbsForwardFragment<U> {

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
