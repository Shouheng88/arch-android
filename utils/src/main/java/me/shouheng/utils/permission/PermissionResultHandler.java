package me.shouheng.utils.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import me.shouheng.utils.permission.callback.PermissionResultCallback;

/**
 * The permission default permission result handler
 *
 * @author WngShhng 2019-04-02 12:22
 */
public class PermissionResultHandler {

    private PermissionResultHandler() {
        throw new UnsupportedOperationException("U can't initialize me!");
    }

    /**
     * The method to call in {@link android.app.Activity#onRequestPermissionsResult(int, String[], int[])}.
     *
     * @param activity the activity
     * @param requestCode request code
     * @param permissions permissions required
     * @param grantResults permission results
     * @param permissionResultCallback callback
     */
    public static void handlePermissionsResult(Activity activity,
                                               int requestCode,
                                               @NonNull String[] permissions,
                                               @NonNull int[] grantResults,
                                               @NonNull PermissionResultCallback permissionResultCallback) {
        // get permissions not granted
        int notGrantedCount = 0;
        List<String> notGranted = new LinkedList<>();
        for (int i=0, length=grantResults.length; i<length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                notGrantedCount++;
                notGranted.add(permissions[i]);
            }
        }

        // check the permissions result and call the callback
        if (notGrantedCount == 0) {
            permissionResultCallback.onGetAllPermissions();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                List<String> rationaleList = new LinkedList<>();
                for (String permission : notGranted) {
                    if (activity.shouldShowRequestPermissionRationale(permission)) {
                        rationaleList.add(permission);
                    }
                }
                if (rationaleList.isEmpty()) {
                    permissionResultCallback.onPermissionNotGranted(notGranted.toArray(new String[0]));
                } else {
                    permissionResultCallback.showPermissionsRationale(rationaleList.toArray(new String[0]));
                }
            } else {
                permissionResultCallback.onPermissionNotGranted(notGranted.toArray(new String[0]));
            }
        }
    }
}
