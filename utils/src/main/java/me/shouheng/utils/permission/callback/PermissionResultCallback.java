package me.shouheng.utils.permission.callback;

/**
 * The permission result callback
 *
 * @author WngShhng 2019-04-02 12:39
 */
public interface PermissionResultCallback {

    /**
     * Callback when got all permission required.
     */
    void onGetAllPermissions();

    /**
     * Should show permissions rational message.
     *
     * @param permissions the permissions to show rations message.
     */
    void showPermissionsRationale(String...permissions);

    /**
     * Callback when permissions not granted. This method will be called
     * when one of permissions required not granted and shouldn't show rational message.
     *
     * @param permissions permissions not granted.
     */
    void onPermissionNotGranted(String...permissions);
}
