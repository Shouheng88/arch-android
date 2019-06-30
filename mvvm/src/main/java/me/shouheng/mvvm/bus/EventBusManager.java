package me.shouheng.mvvm.bus;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;

import static me.shouheng.mvvm.Platform.DEPENDENCY_ANDROID_EVENTBUS;
import static me.shouheng.mvvm.Platform.DEPENDENCY_EVENTBUS;

/**
 * EventBus manager, mainly used to support EventBus and AndroidEventBus environment.
 *
 * @author WngShhng 2019-6-29
 */
public final class EventBusManager {

    private static volatile EventBusManager manager;

    public static EventBusManager getInstance() {
        if (manager == null) {
            synchronized (EventBusManager.class) {
                if (manager == null) {
                    manager = new EventBusManager();
                }
            }
        }
        return manager;
    }

    private EventBusManager() {
    }

    public void register(Object subscriber) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().register(subscriber);
        }
        if (DEPENDENCY_EVENTBUS && haveAnnotation(subscriber)) {
            org.greenrobot.eventbus.EventBus.getDefault().register(subscriber);
        }
    }

    public void unregister(Object subscriber) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().unregister(subscriber);
        }
        if (DEPENDENCY_EVENTBUS && haveAnnotation(subscriber)) {
            org.greenrobot.eventbus.EventBus.getDefault().unregister(subscriber);
        }
    }

    public void post(Object event) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().post(event);
        }
        if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.getDefault().post(event);
        }
    }

    public void postSticky(Object event) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().postSticky(event);
        }
        if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.getDefault().postSticky(event);
        }
    }

    public <T> T removeStickyEvent(Class<T> eventType) {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().removeStickyEvent(eventType);
            return null;
        }
        if (DEPENDENCY_EVENTBUS) {
            return org.greenrobot.eventbus.EventBus.getDefault().removeStickyEvent(eventType);
        }
        return null;
    }

    public void clear() {
        if (DEPENDENCY_ANDROID_EVENTBUS) {
            org.simple.eventbus.EventBus.getDefault().clear();
        }
        if (DEPENDENCY_EVENTBUS) {
            org.greenrobot.eventbus.EventBus.clearCaches();
        }
    }

    /*----------------------------------------inner methods--------------------------------------*/

    /**
     * We need to detect that whether the subscriber has one method:
     * used {@link Subscribe} annotation and has more than one parameters.
     * This is necessary, since if we registered a subscriber don't meet the requirements,
     * it will throw an exception.
     *
     * @param subscriber the subscriber.
     * @return does the subscriber has at least one method required.
     */
    private boolean haveAnnotation(Object subscriber) {
        boolean skipSuperClasses = false;
        Class<?> clazz = subscriber.getClass();
        while (clazz != null && !isSystemClass(clazz.getName()) && !skipSuperClasses) {
            Method[] allMethods;
            try {
                allMethods = clazz.getDeclaredMethods();
            } catch (Throwable th) {
                try {
                    allMethods = clazz.getMethods();
                }catch (Throwable th2) {
                    continue;
                }finally {
                    skipSuperClasses = true;
                }
            }
            for (Method method : allMethods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (method.isAnnotationPresent(Subscribe.class) && parameterTypes.length == 1) {
                    return true;
                }
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    private boolean isSystemClass(String name) {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.");
    }
}
