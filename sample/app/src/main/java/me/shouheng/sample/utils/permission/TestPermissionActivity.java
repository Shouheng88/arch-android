package me.shouheng.sample.utils.permission;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import me.shouheng.sample.R;
import me.shouheng.utils.permission.callback.OnGetPermissionCallback;
import me.shouheng.utils.permission.Permission;
import me.shouheng.utils.permission.callback.PermissionResultCallback;
import me.shouheng.utils.permission.callback.PermissionResultCallbackImpl;
import me.shouheng.utils.permission.PermissionResultHandler;
import me.shouheng.utils.permission.PermissionResultResolver;
import me.shouheng.utils.permission.PermissionUtils;

/**
 * To use {@link PermissionUtils} to request permissions, you need:
 * 1. Let your activity implement {@link PermissionResultResolver}. It's suggested that
 * you implement this interface in your base activity.
 * 2. Override {@link #onRequestPermissionsResult(int, String[], int[])} and call
 * {@link PermissionResultHandler#handlePermissionsResult(
 *  Activity, int, String[], int[], PermissionResultCallback)}.
 * 3. Call {@link PermissionUtils#checkPermission(Activity, int, OnGetPermissionCallback)}
 *  to request given permissions.
 *
 * @author shouh
 * @version $Id: TestPermissionActivity, v 0.1 2018/11/22 12:13 shouh Exp$
 */
public class TestPermissionActivity extends AppCompatActivity implements PermissionResultResolver {

    private OnGetPermissionCallback onGetPermissionCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_test);

        findViewById(R.id.btn_storage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkStoragePermission(TestPermissionActivity.this,
                        new OnGetPermissionCallback() {
                            @Override
                            public void onGetPermission() {
                                Toast.makeText(TestPermissionActivity.this,
                                        R.string.permission_get_storage_permission,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkCameraPermission(TestPermissionActivity.this,
                        new OnGetPermissionCallback() {
                            @Override
                            public void onGetPermission() {
                                Toast.makeText(TestPermissionActivity.this,
                                        R.string.permission_get_camera_permission,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        findViewById(R.id.btn_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkLocationPermission(TestPermissionActivity.this,
                        new OnGetPermissionCallback() {
                            @Override
                            public void onGetPermission() {
                                Toast.makeText(TestPermissionActivity.this,
                                        R.string.permission_get_location_permission,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        findViewById(R.id.btn_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkPermissions(TestPermissionActivity.this,
                        new OnGetPermissionCallback() {
                            @Override
                            public void onGetPermission() {
                                Toast.makeText(TestPermissionActivity.this,
                                        R.string.permission_get_permissions,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, Permission.CAMERA, Permission.LOCATION, Permission.STORAGE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // call permission result handler
        PermissionResultHandler.handlePermissionsResult(this, requestCode, permissions,
                grantResults, new PermissionResultCallbackImpl(this, onGetPermissionCallback));
    }

    @Override
    public void setOnGetPermissionCallback(OnGetPermissionCallback onGetPermissionCallback) {
        this.onGetPermissionCallback = onGetPermissionCallback;
    }
}
