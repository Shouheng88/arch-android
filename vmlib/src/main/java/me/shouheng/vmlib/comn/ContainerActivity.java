package me.shouheng.vmlib.comn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import me.shouheng.uix.pages.web.FragmentKeyDown;
import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.constant.ActivityDirection;
import me.shouheng.utils.stability.L;
import me.shouheng.vmlib.Platform;
import me.shouheng.vmlib.R;
import me.shouheng.vmlib.base.BaseActivity;

/**
 * The common container activity for ONE SHOT fragment.
 *
 * Call {@link #open(Class)} or {@link #openFragment(Class)} directly to get a builder for
 * container and then set arguments by builder for fragment.
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 */
public class ContainerActivity extends BaseActivity<EmptyViewModel> {

    /** Key for the {@link Fragment} class used to create the fragment instance. */
    public static final String KEY_EXTRA_FRAGMENT_CLASS         = "__extra_key_fragment_class";

    /** Key for fragment providers. */
    public static final String KEY_EXTRA_FRAGMENT_PROVIDER      = "__extra_key_fragment_provider";

    /** Key for theme of current activity. */
    public static final String KEY_EXTRA_THEME_ID               = "__extra_key_theme_id";

    /** Key for the activity finish direction. */
    public static final String KEY_EXTRA_ACTIVITY_DIRECTION     = "__extra_key_activity_direction";

    /** The activity finish direction. */
    @ActivityDirection private int activityFinishDirection = ActivityDirection.ANIMATE_NONE;

    /**
     * Get a {@link ActivityUtils.Builder} object to build a request for container activity.
     * You can set argument of your fragment by the methods provided by the builder directly.
     * NOTE : THE BUNDLE FROM ACTIVITY INTENT WILL BE USED AS THE ARGUMENTS OF FRAGMENT.
     *
     * @param fragment  the fragment class
     * @param <F>       the fragment class type
     * @return          the activity builder object
     * @see #openFragment(Class) for {@link android.app.Fragment}
     */
    public static <F extends Fragment> ActivityUtils.Builder<ContainerActivity> open(Class<F> fragment) {
        return ActivityUtils.open(ContainerActivity.class).put(KEY_EXTRA_FRAGMENT_CLASS, fragment);
    }

    /**
     * Get a {@link ActivityUtils.Builder} for {@link android.app.Fragment}.
     *
     * NOTE : THE BUNDLE FROM ACTIVITY INTENT WILL BE USED AS THE ARGUMENTS OF FRAGMENT.
     *
     * @param fragment fragment
     * @param <F>      fragment type
     * @return         the builder
     * @see #open(Class) for {@link Fragment}
     */
    public static <F extends android.app.Fragment> ActivityUtils.Builder<ContainerActivity> openFragment(Class<F> fragment) {
        return ActivityUtils.open(ContainerActivity.class).put(KEY_EXTRA_FRAGMENT_CLASS, fragment);
    }

    /** Launch container with fragment provider. */
    public static <P extends FragmentProvider> ActivityUtils.Builder<ContainerActivity> openWith(Class<P> provider) {
        return ActivityUtils.open(ContainerActivity.class).put(KEY_EXTRA_FRAGMENT_PROVIDER, provider);
    }

    /** Launch container with fragment provider. */
    public static <P extends AppFragmentProvider> ActivityUtils.Builder<ContainerActivity> openFragmentWith(Class<P> provider) {
        return ActivityUtils.open(ContainerActivity.class).put(KEY_EXTRA_FRAGMENT_PROVIDER, provider);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // If you want to use a custom theme for current fragment container, use
        // #KEY_EXTRA_THEME_ID to specify the theme id. Sometimes, for example,
        // if we used "?attr/xxxx" as custom attribute to customize themes, we have
        // to change the theme of activity as well. So, be able to custom theme of
        // container was a necessary.
        int themeId = getIntent().getIntExtra(KEY_EXTRA_THEME_ID, -1);
        if (themeId != -1) setTheme(themeId);
        activityFinishDirection = getIntent().getIntExtra(
                KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_NONE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.vmlib_activity_container;
    }

    @Override
    protected void doCreateView(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        Class fragmentClass = (Class) intent.getSerializableExtra(KEY_EXTRA_FRAGMENT_CLASS);
        Class providerClass = (Class) intent.getSerializableExtra(KEY_EXTRA_FRAGMENT_PROVIDER);
        if (fragmentClass == null && providerClass == null) {
            L.w("Warn: no fragment to add.");
            return;
        }
        if (fragmentClass != null) {
            instanceAndReplace(savedInstanceState, fragmentClass);
        } else {
            provideAndReplace(savedInstanceState, providerClass);
        }
    }

    private void instanceAndReplace(@Nullable Bundle savedInstanceState, Class fragmentClass) {
        if (Fragment.class.isAssignableFrom(fragmentClass)) {
            // fix 2022-10-03 add new judge
            instanceFragmentAndReplace(savedInstanceState, fragmentClass);
        } else if (android.app.Fragment.class.isAssignableFrom(fragmentClass)) {
            instanceAppFragmentAndReplace(savedInstanceState, fragmentClass);
        }
    }

    /** Instance a fragment and add to container. */
    private void instanceFragmentAndReplace(@Nullable Bundle savedInstanceState, Class fragmentClass) {
        Bundle arguments = getIntent().getExtras();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null || savedInstanceState == null || fragment.getClass() != fragmentClass) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(arguments);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            } catch (IllegalAccessException e) {
                L.e(e);
            } catch (InstantiationException e) {
                L.e(e);
            }
        }
    }

    /** Instance a app fragment and add to container. */
    private void instanceAppFragmentAndReplace(@Nullable Bundle savedInstanceState, Class fragmentClass) {
        Bundle arguments = getIntent().getExtras();
        android.app.Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
        if (fragment == null || savedInstanceState == null || fragment.getClass() != fragmentClass) {
            try {
                fragment = (android.app.Fragment) fragmentClass.newInstance();
                fragment.setArguments(arguments);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            } catch (IllegalAccessException e) {
                L.e(e);
            } catch (InstantiationException e) {
                L.e(e);
            }
        }
    }

    private void provideAndReplace(@Nullable Bundle savedInstanceState, Class providerClass) {
        if (FragmentProvider.class.isAssignableFrom(providerClass)) {
            Bundle arguments = getIntent().getExtras();
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (fragment == null || savedInstanceState == null) {
                try {
                    FragmentProvider provider = (FragmentProvider) providerClass.newInstance();
                    fragment = provider.get(this, arguments);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                } catch (IllegalAccessException e) {
                    L.e(e);
                } catch (InstantiationException e) {
                    L.e(e);
                }
            }
        } else if (AppFragmentProvider.class.isAssignableFrom(providerClass)) {
            Bundle arguments = getIntent().getExtras();
            android.app.Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
            if (fragment == null || savedInstanceState == null) {
                try {
                    AppFragmentProvider provider = (AppFragmentProvider) providerClass.newInstance();
                    fragment = provider.get(this, arguments);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                } catch (IllegalAccessException e) {
                    L.e(e);
                } catch (InstantiationException e) {
                    L.e(e);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (Platform.DEPENDENCY_UIX_ANALYTICS
                && fragment instanceof FragmentKeyDown
                && (((FragmentKeyDown) fragment).onFragmentKeyDown(keyCode, event))) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof BackEventResolver) {
            ((BackEventResolver) fragment).onBackPressed(this);
        } else {
            superOnBackPressed();
        }
    }

    @Override
    public void superOnBackPressed() {
        super.superOnBackPressed();
        if (activityFinishDirection != ActivityDirection.ANIMATE_NONE) {
            ActivityUtils.overridePendingTransition(this, activityFinishDirection);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (activityFinishDirection != ActivityDirection.ANIMATE_NONE) {
            ActivityUtils.overridePendingTransition(this, activityFinishDirection);
        }
    }

    /** Back event resolver for fragment. */
    public interface BackEventResolver {

        /** Called when the back button is pressed. */
        void onBackPressed(ContainerActivity activity);
    }

    /** A provider for current fragment. */
    public interface FragmentProvider {
        /**
         * Method to get a fragment instance.
         *
         * @param activity the container activity
         * @param intentExtras the extras from {@link Activity#getIntent()}
         */
        Fragment get(ContainerActivity activity, Bundle intentExtras);
    }

    /** A provider for current fragment. */
    public interface AppFragmentProvider {
        /**
         * Method to get a fragment instance.
         *
         * @param activity the container activity
         * @param intentExtras the extras from {@link Activity#getIntent()}
         */
        android.app.Fragment get(ContainerActivity activity, Bundle intentExtras);
    }
}
