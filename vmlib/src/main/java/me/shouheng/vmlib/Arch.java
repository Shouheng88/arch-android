package me.shouheng.vmlib;

import android.app.Application;

import me.shouheng.utils.UtilsApp;
import me.shouheng.vmlib.network.NetworkStateManager;

/**
 * ===================================================================
 *
 *                            The VMLib
 *
 *                       == ShouhengWang ==
 *
 *        AN EASY AND CONVENIENT MVVM ARCHITECTURE FOR ANDROID.
 *
 * ==================================================================
 *
 * Call {@link Arch#onCreate(Application)} in your application to initialize the library.
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version Date: 2019-6-29
 */
public final class Arch {

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
        Arch.app = application;
        UtilsApp.init(application);
        NetworkStateManager.INSTANCE.init(application);
    }
}
