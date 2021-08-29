package me.shouheng.vmlib.base;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;

/**
 * The basic common implementation for MMVMs activity.
 *
 * @author <a href="mailto:shouheng2020@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public abstract class CommonActivity<U extends BaseViewModel, T extends ViewDataBinding> extends BaseActivity<U> {

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
