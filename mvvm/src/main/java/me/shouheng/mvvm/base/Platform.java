package me.shouheng.mvvm.base;

/**
 * The class used to detect the dependencies.
 *
 * @author WngShhng
 */
public class Platform {
    public static final boolean DEPENDENCY_ANDROID_EVENTBUS;
    public static final boolean DEPENDENCY_EVENTBUS;

    static {
        DEPENDENCY_ANDROID_EVENTBUS = findClassByClassName("org.simple.eventbus.EventBus");
        DEPENDENCY_EVENTBUS = findClassByClassName("org.greenrobot.eventbus.EventBus");
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