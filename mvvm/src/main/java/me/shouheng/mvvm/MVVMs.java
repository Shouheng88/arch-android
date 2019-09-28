package me.shouheng.mvvm;

import android.app.Application;
import android.content.Context;
import me.shouheng.utils.UtilsApp;

/**
 * ========================================================
 *
 *          MM    MM VV     VV VV     VV MM    MM
 *          MMM  MMM VV     VV VV     VV MMM  MMM
 *          MM MM MM  VV   VV   VV   VV  MM MM MM
 *          MM    MM   VV VV     VV VV   MM    MM
 *          MM    MM    VVV       VVV    MM    MM
 *
 *                     == WngShhng ==
 *
 *    AN EASY AND CONVENIENT MVVM ARCHITECTURE FOR ANDROID.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version Date: 2019-6-29
 */
public final class MVVMs {

    private static Application app;

    /**
     * Get the application
     *
     * @return the application
     */
    public static Application getApp() {
        if (app == null) {
            throw new IllegalStateException("Sorry, you should call MVVMs.onCreate() " +
                    "method on your custom application first.");
        }
        return app;
    }

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
        MVVMs.app = application;
        UtilsApp.init(application);
    }
}
