package me.shouheng.utils.permission.callback;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.widget.Toast;

import me.shouheng.utils.R;
import me.shouheng.utils.permission.Permission;
import me.shouheng.utils.permission.PermissionUtils;

/**
 * {@link PermissionResultCallback} 的默认实现
 *
 * @author WngShhng 2019-04-02 12:40
 */
public class PermissionResultCallbackImpl implements PermissionResultCallback {

    private Context context;

    private OnGetPermissionCallback onGetPermissionCallback;

    public PermissionResultCallbackImpl(Context context, OnGetPermissionCallback onGetPermissionCallback) {
        this.context = context;
        this.onGetPermissionCallback = onGetPermissionCallback;
    }

    @Override
    public void onGetAllPermissions() {
        if (onGetPermissionCallback != null) {
            onGetPermissionCallback.onGetPermission();
        }
    }

    @Override
    public void showPermissionsRationale(String... permissions) {
        String names = Permission.names(context, permissions);
        String message = context.getResources().getQuantityString(
                R.plurals.permission_set_in_settings_message, permissions.length, names);
        new AlertDialog.Builder(context)
                .setTitle(R.string.permission_set_permission)
                .setMessage(Html.fromHtml(message))
                .setPositiveButton(R.string.permission_to_set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toSetPermission(context);
                    }
                })
                .setNegativeButton(R.string.permission_cancel, null)
                .create().show();
    }

    @Override
    public void onPermissionNotGranted(String... permissions) {
        String names = Permission.names(context, permissions);
        String message = context.getResources().getQuantityString(
                R.plurals.permission_denied_message, permissions.length, names);
        Toast.makeText(context, Html.fromHtml(message), Toast.LENGTH_SHORT).show();
    }
}
