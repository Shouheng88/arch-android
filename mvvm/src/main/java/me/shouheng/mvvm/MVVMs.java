package me.shouheng.mvvm;

import android.app.Application;
import android.content.Context;
import me.shouheng.utils.UtilsApp;

/**
 * MVVMs, easy and convenient MVVM architecture for Android.
 *  __  __  _  _  _  _  __  __  ___
 * (  \/  )( )( )( )( )(  \/  )/ __)
 *  )    (  \\//  \\//  )    ( \__ \
 * (_/\/\_) (__)  (__) (_/\/\_)(___/
 *
 * @author WngShhng shouheng2015@gmail.com 2019-6-29
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
