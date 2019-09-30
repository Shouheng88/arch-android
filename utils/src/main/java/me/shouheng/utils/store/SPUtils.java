package me.shouheng.utils.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import me.shouheng.utils.UtilsApp;
import me.shouheng.utils.data.StringUtils;

/**
 * 封装基于 {@link android.content.SharedPreferences} 的持久化工具
 *
 * @author WngShhng 2019-05-08 21:30
 */
public final class SPUtils {

    private static final Map<String, SPUtils> SP_UTILS_MAP = new ConcurrentHashMap<>();
    private SharedPreferences sp;

    /*-------------------------------------get instance----------------------------------------*/

    public static SPUtils getInstance() {
        return getInstance("", Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(final int mode) {
        return getInstance("", mode);
    }

    public static SPUtils getInstance(String spName) {
        return getInstance(spName, Context.MODE_PRIVATE);
    }

    public static SPUtils getInstance(String spName, final int mode) {
        if (StringUtils.isSpace(spName)) spName = getDefaultSharedPreferencesName();
        SPUtils spUtils = SP_UTILS_MAP.get(spName);
        if (spUtils == null) {
            spUtils = new SPUtils(spName, mode);
            SP_UTILS_MAP.put(spName, spUtils);
        }
        return spUtils;
    }

    /*-------------------------------------instance methods----------------------------------------*/

    public void put(@NonNull final String key, final String value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final String value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putString(key, value).commit();
        } else {
            sp.edit().putString(key, value).apply();
        }
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void put(@NonNull final String key, final int value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final int value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putInt(key, value).commit();
        } else {
            sp.edit().putInt(key, value).apply();
        }
    }

    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    public int getInt(@NonNull final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void put(@NonNull final String key, final long value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final long value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putLong(key, value).commit();
        } else {
            sp.edit().putLong(key, value).apply();
        }
    }

    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    public long getLong(@NonNull final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void put(@NonNull final String key, final float value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putFloat(key, value).commit();
        } else {
            sp.edit().putFloat(key, value).apply();
        }
    }

    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    public float getFloat(@NonNull final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void put(@NonNull final String key, final boolean value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final boolean value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putBoolean(key, value).commit();
        } else {
            sp.edit().putBoolean(key, value).apply();
        }
    }

    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void put(@NonNull final String key, final Set<String> value) {
        put(key, value, false);
    }

    public void put(@NonNull final String key, final Set<String> value, final boolean isCommit) {
        if (isCommit) {
            sp.edit().putStringSet(key, value).commit();
        } else {
            sp.edit().putStringSet(key, value).apply();
        }
    }

    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    public Set<String> getStringSet(@NonNull final String key, final Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }

    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    public void remove(@NonNull final String key) {
        remove(key, false);
    }

    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    public void clear() {
        clear(false);
    }

    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }

    public void setOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }

    /*-------------------------------------inner methods----------------------------------------*/

    private SPUtils(final String spName) {
        sp = UtilsApp.getApp().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    private SPUtils(final String spName, final int mode) {
        sp = UtilsApp.getApp().getSharedPreferences(spName, mode);
    }

    private static String getDefaultSharedPreferencesName() {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            return PreferenceManager.getDefaultSharedPreferencesName(UtilsApp.getApp());
        } else {
            return UtilsApp.getApp().getPackageName() + "_preferences";
        }
    }

}
