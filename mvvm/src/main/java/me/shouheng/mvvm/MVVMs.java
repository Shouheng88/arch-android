package me.shouheng.mvvm;

import android.app.Application;
import android.content.Context;
import me.shouheng.utils.UtilsApp;

/**
 * Initialize MVVMs framework.
 *
 * @author WngShhng 2019-6-29
 */
public final class MVVMs {

    /**
     * Call this method in your custom {@link Application#attachBaseContext(Context)}
     *
     * @param context the context
     */
    public static void attachBaseContext(Context context) {
        // no-op
    }

    /**
     * Call this method in your custom {@link Application#onCreate()} method.
     *
     * @param application the application
     */
    public static void onCreate(Application application) {
        UtilsApp.init(application);
    }
}
