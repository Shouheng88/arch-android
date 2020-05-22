package me.shouheng.vmlib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import me.shouheng.utils.permission.PermissionResultResolver;

/**
 * The basic common implementation for MMVMs activity.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-6-29
 */
public abstract class CommonActivity<U extends BaseViewModel, T extends ViewDataBinding>
        extends BaseActivity<U>
        implements PermissionResultResolver {

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
