package me.shouheng.mvvm.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.lang.reflect.ParameterizedType;

/**
 * The basic common implementation for MMVMs.
 *
 * @author WngShhng 2019-6-29
 */
public abstract class CommonActivity<T extends ViewDataBinding, VM extends CommonViewModel> extends AppCompatActivity {

    /**
     * The base view model.
     */
    private VM vm;

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
     * Initialize view model. Override this method to add your own implementation.
     *
     * @return the view model will be used.
     */
    protected VM initViewModel() {
        Class<VM> vmClass = ((Class)((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
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
        if (getLayoutResId() <= 0) {
            throw new IllegalArgumentException("The subclass must provider a valid layout resources id.");
        }
        vm = initViewModel();
        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayoutResId(), null, false);
        beforeSetContentView(savedInstanceState);
        setContentView(binding.getRoot());
        doCreateView(savedInstanceState);
    }

    protected VM getVM() {
        return vm;
    }

    protected T getBinding() {
        return binding;
    }

    /**
     * Get current fragnent of given resources id.
     *
     * @param resId the resources id.
     * @return the fragment.
     */
    protected Fragment getCurrentFragment(@IdRes int resId) {
        return getSupportFragmentManager().findFragmentById(resId);
    }

    /**
     * This method is used to call the super {@link #onBackPressed()} instead of the
     * implementation of current activity. Since the current {@link #onBackPressed()} may be override.
     */
    public void superOnBackPressed() {
        super.onBackPressed();
    }
}
