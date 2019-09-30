package me.shouheng.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import android.util.Log;
import me.shouheng.utils.app.AppUtils;

/**
 * ========================================================================================================================
 *
 *
 *       .o.                         .o8                      o8o        .o8     ooooo     ooo     .    o8o  oooo
 *      .888.                       "888                      `"'       "888     `888'     `8'   .o8    `"'  `888
 *     .8"888.     ooo. .oo.    .oooo888  oooo d8b  .ooooo.  oooo   .oooo888      888       8  .o888oo oooo   888   .oooo.o
 *    .8' `888.    `888P"Y88b  d88' `888  `888""8P d88' `88b `888  d88' `888      888       8    888   `888   888  d88(  "8
 *   .88ooo8888.    888   888  888   888   888     888   888  888  888   888      888       8    888    888   888  `"Y88b.
 *  .8'     `888.   888   888  888   888   888     888   888  888  888   888      `88.    .8'    888 .  888   888  o.  )88b
 * o88o     o8888o o888o o888o `Y8bod88P" d888b    `Y8bod8P' o888o `Y8bod88P"       `YbodP'      "888" o888o o888o 8""888P'
 *
 *                                                 == WngShhng ==
 *
 *                                    AN COLLECTION OF USEFUL UTILS IN ANDROID
 *
 * ========================================================================================================================
 *
 * To initialize this library in your project, you should simply call {@link UtilsApp#init(Application)} in your application:
 *
 * <code>
 * public class SampleApp extends Application {
 *
 *     public void onCreate() {
 *         super.onCreate();
 *         // initialize the utils library
 *         UtilsApp.init(this);
 *     }
 * }
 * </code>
 *
 * @author WngShhng shouheng2015@gmail.com
 * @version 2019-05-07 12:13
 */
public final class UtilsApp {

    private static final String TAG = "UtilsApp";

    private static Application app;

    private static boolean isForeGround = false;

    private UtilsApp() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }

    /**
     * Get the application
     *
     * @return the application
     */
    public static Application getApp() {
        if (app == null) {
            throw new IllegalStateException("Sorry, you must call UtilsApp#init() method at first!");
        }
        return app;
    }

    /**
     * Initialize with given application, the app is usually used to get the app context.
     *
     * @param app the app used to initialize the utils library.
     */
    public static void init(Application app) {
        UtilsApp.app = app;
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            private int activityCount = 0;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppUtils.attachActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                AppUtils.attachForeActivity(activity);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (!isForeGround) {
                    isForeGround = true;
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                // no-op
            }

            @Override
            public void onActivityStopped(Activity activity) {
                AppUtils.detachForeActivity(activity);
                activityCount--;
                if (activityCount == 0) {
                    isForeGround = false;
                    Log.i(TAG, "Activity foreground: " + System.currentTimeMillis());
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                // no-op
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppUtils.detachActivity(activity);
            }
        });
    }
}
