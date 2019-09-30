package me.shouheng.mvvm.utils;

/**
 * The class used to detect the dependencies.
 *
 * @author <a herf="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
public class Platform {
    public static final boolean DEPENDENCY_ANDROID_EVENTBUS;
    public static final boolean DEPENDENCY_EVENTBUS;
    public static final boolean DEPENDENCY_UMENG_ANALYTICS;

    static {
        DEPENDENCY_ANDROID_EVENTBUS = findClassByClassName("org.simple.eventbus.EventBus");
        DEPENDENCY_EVENTBUS = findClassByClassName("org.greenrobot.eventbus.EventBus");
        DEPENDENCY_UMENG_ANALYTICS = findClassByClassName("com.umeng.analytics.MobclickAgent");
    }

    private static boolean findClassByClassName(String className) {
        boolean hasDependency;
        try {
            Class.forName(className);
            hasDependency = true;
        } catch (ClassNotFoundException e) {
            hasDependency = false;
        }
        return hasDependency;
    }
}