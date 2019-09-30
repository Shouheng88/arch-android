package me.shouheng.utils.permission;

import android.app.Activity;

import me.shouheng.utils.permission.callback.OnGetPermissionCallback;

/**
 * Permission result resolver interface.
 * The permission checker activity must implement this interface.
 *
 * @author WngShhng 2019-04-02 12:08
 */
public interface PermissionResultResolver {

    /**
     * Set the permission callback. The {@link OnGetPermissionCallback} was used in
     * {@link PermissionUtils#checkPermissions(Activity, OnGetPermissionCallback, int...)}
     * for user to handle their business when finally got permission.
     *
     * @param onGetPermissionCallback the permission callback
     */
    void setOnGetPermissionCallback(OnGetPermissionCallback onGetPermissionCallback);
}
