package me.shouheng.mvvm.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.ParameterizedType;

/**
 * The basic common implementation for MMVMs activity.
 *
 * @author WngShhng
 * @version 2019-6-29
 */
public abstract class CommonActivity<T extends ViewDataBinding, U extends BaseViewModel> extends AppCompatActivity {

    private U vm;

    private T binding;

    private int layoutResId;

    /**
     * Do create view business.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected abstract void doCreateView(@Nullable Bundle savedInstanceState);

    /**
     * Initialize view model. Override this method to add your own implementation.
     *
     * @return the view model will be used.
     */
    protected U createViewModel() {
        Class<U> vmClass = ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
        return ViewModelProviders.of(this).get(vmClass);
    }

    /**
     * This method will be called before the {@link #setContentView(View)} was called.
     *
     * @param savedInstanceState the saved instance state.
     */
    protected void beforeSetContentView(@Nullable Bundle savedInstanceState) {
        // default no implementation
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() != 0) {
            layoutResId = getLayoutResId();
        }
        if (layoutResId <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        vm = createViewModel();
        vm.onCreate(getIntent().getExtras(), savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), layoutResId, null, false);
        beforeSetContentView(savedInstanceState);
        setContentView(binding.getRoot());
        doCreateView(savedInstanceState);
    }

    protected U getVM() {
        return vm;
    }

    protected T getBinding() {
        return binding;
    }

    /**
     * Get the layout resource id from subclass.
     *
     * @return layout resource id.
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        vm.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        vm.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        vm.onDestroy();
        super.onDestroy();
    }
}
