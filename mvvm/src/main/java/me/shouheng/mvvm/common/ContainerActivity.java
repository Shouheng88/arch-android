package me.shouheng.mvvm.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import me.shouheng.mvvm.R;
import me.shouheng.mvvm.base.CommonActivity;
import me.shouheng.mvvm.databinding.MvvmsActivityContainerBinding;
import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.stability.LogUtils;

/**
 * The common container activity for fragment:
 * Call {@link #open(Class)} method directly to get a builder for container. Sample:
 *
 * <code>
 * ContainerActivity.open(SampleFragment::class.java)
 *     // set arguments for the fragment
 *     .put(SampleFragment.ARGS_KEY_TEXT, "Here is the text from the arguments.")
 *     // launch the container activity
 *     .launch(context!!)
 * </code>
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
public class ContainerActivity extends CommonActivity<MvvmsActivityContainerBinding, EmptyViewModel> {

    /**
     * Key for extra: the fragment class used to create the fragment instance.
     */
    public static final String KEY_EXTRA_CLASS = "__extra_key_fragment_class";

    /**
     * Get a {@link ActivityUtils.Builder} object to build a request for container activity.
     * You can set argument of your fragment by the methods provided by the builder directly.
     *
     * @param fragment  the fragment class
     * @param <F>       the fragment class type
     * @return          the activity builder object
     */
    public static <F extends Fragment> ActivityUtils.Builder<ContainerActivity> open(Class<F> fragment) {
        return ActivityUtils.open(ContainerActivity.class).put(KEY_EXTRA_CLASS, fragment);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.mvvms_activity_container;
    }

    @Override
    protected void doCreateView(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        Class<Fragment> fragmentClass = (Class<Fragment>) intent.getSerializableExtra(KEY_EXTRA_CLASS);
        Bundle bundle = intent.getExtras();
        try {
            Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (InstantiationException e) {
            LogUtils.d(e);
        } catch (IllegalAccessException e) {
            LogUtils.d(e);
        }
    }
}
