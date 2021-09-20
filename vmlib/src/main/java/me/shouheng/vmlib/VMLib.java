package me.shouheng.vmlib;

import android.app.Application;

import me.shouheng.uix.common.UIX;
import me.shouheng.utils.UtilsApp;

/**
 * ===================================================================
 *
 *                            The VMLib
 *
 *                         == WngShhng ==
 *
 *        AN EASY AND CONVENIENT MVVM ARCHITECTURE FOR ANDROID.
 *
 * ==================================================================
 *
 * Call {@link VMLib#onCreate(Application)} in your application to initialize the library.
 *
 * @author <a href="mailto:shouheng2020@gmail.com">WngShhng</a>
 * @version Date: 2019-6-29
 */
public final class VMLib {

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
     * Call this method in your custom {@link Application#onCreate()} method.
     *
     * @param application the application
     */
    public static void onCreate(Application application) {
        VMLib.app = application;
        UtilsApp.init(application);
        // If you want to use uix, you don't need to initialize it in your application manually.
        if (Platform.DEPENDENCY_UIX_ANALYTICS) UIX.INSTANCE.init(application);
    }
}
