package me.shouheng.vmlib.base;

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
 * @FragmentConfiguration
 * class SampleFragment : CommonFragment<EmptyViewModel, FragmentSampleBinding>() {
 *
 *     companion object {
 *         const val ARGS_KEY_TEXT = "__args_key_text"
 *     }
 *
 *     override fun getLayoutResId(): Int = R.layout.fragment_sample
 *
 *     override fun doCreateView(savedInstanceState: Bundle?) {
 *         val text = arguments?.getString(ARGS_KEY_TEXT)
 *         binding.tv.text = text
 *     }
 *
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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
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
