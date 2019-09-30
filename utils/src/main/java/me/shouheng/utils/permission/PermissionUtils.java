package me.shouheng.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.LinkedList;
import java.util.List;

import me.shouheng.utils.UtilsApp;
import me.shouheng.utils.permission.Permission.PermissionCode;
import me.shouheng.utils.permission.callback.OnGetPermissionCallback;
import me.shouheng.utils.permission.callback.PermissionResultCallback;

/**
 * 用来获取运行时权限的工具类
 *
 * 调用该类的时候需要传入一个 {@link Activity}，并且要求：
 * 1. 实现了 {@link PermissionResultResolver}，并且
 * 2. 在它的 {@link Activity#onRequestPermissionsResult(int, String[], int[])} 中
 * 调用了 {@link PermissionResultHandler#handlePermissionsResult(
 * Activity, int, String[], int[], PermissionResultCallback)} 方法。
 *
 * Sample code below:
 *
 * <code>
 *public class TestPermissionActivity extends AppCompatActivity implements PermissionResultResolver {
 *
 *     private OnGetPermissionCallback onGetPermissionCallback;
 *
 *     @Override
 *     protected void onCreate(@Nullable Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_permission_test);
 *
 *         findViewById(R.id.btn_storage).setOnClickListener(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View v) {
 *                 // do check permission
 *                 PermissionUtils.checkStoragePermission(TestPermissionActivity.this,
 *                         new OnGetPermissionCallback() {
 *                             @Override
 *                             public void onGetPermission() {
 *                                 // you logic when get permission
 *                                 Toast.makeText(TestPermissionActivity.this,
 *                                         R.string.permission_get_storage_permission,
 *                                         Toast.LENGTH_SHORT).show();
 *                             }
 *                         });
 *             }
 *         });
 *     }
 *
 *     @Override
 *     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 *         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 *         // call permission result handler
 *         PermissionResultHandler.handlePermissionsResult(this, requestCode, permissions,
 *                 grantResults, new PermissionResultCallbackImpl(this, onGetPermissionCallback));
 *     }
 *
 *     @Override
 *     public void setOnGetPermissionCallback(OnGetPermissionCallback onGetPermissionCallback) {
 *         this.onGetPermissionCallback = onGetPermissionCallback;
 *     }
 * }
 * </code>
 *
 * Created by WngShhng on 2017/12/5.
 */
public final class PermissionUtils {

    private PermissionUtils() {
        throw new UnsupportedOperationException("U can't initialize me!");
    }

    /**
     * 请求存储空间权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkStoragePermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.STORAGE);
    }

    /**
     * 请求手机状态权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkPhonePermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.PHONE_STATE);
    }

    /**
     * 请求位置权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkLocationPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.LOCATION);
    }

    /**
     * 请求录音权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkRecordPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.MICROPHONE);
    }

    /**
     * 请求短信权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkSmsPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.SMS);
    }

    /**
     * 请求传感器权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public static <T extends Activity & PermissionResultResolver> void checkSensorsPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.SENSORS);
    }

    /**
     * 请求联系人权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkContactsPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.CONTACTS);
    }

    /**
     * 请求相机权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkCameraPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.CAMERA);
    }

    /**
     * 请求日历权限
     *
     * @param activity 对应的 Activity
     * @param callback 获取到权限时的回调
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkCalendarPermission(
            @NonNull T activity, OnGetPermissionCallback callback) {
        checkPermissions(activity, callback, Permission.CALENDAR);
    }

    /**
     * 同时请求多组权限
     *
     * @param activity    对应的 Activity
     * @param callback    获取到权限时的回调
     * @param permissions 要获取的权限
     * @param <T> 需要同时实现 {@link PermissionResultResolver}
     */
    public static <T extends Activity & PermissionResultResolver> void checkPermissions(
            @NonNull T activity, OnGetPermissionCallback callback, @PermissionCode int...permissions) {
        activity.setOnGetPermissionCallback(callback);
        // map permission code
        int length = permissions.length;
        String[] standardPermissions = new String[length];
        for (int i=0; i<length; i++) {
            standardPermissions[i] = Permission.map(permissions[i]);
        }
        // check permissions
        int notGrantedCount = 0;
        List<String> notGranted = new LinkedList<>();
        for (int i=0; i<length; i++) {
            if (ContextCompat.checkSelfPermission(activity, standardPermissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                notGrantedCount++;
                notGranted.add(standardPermissions[i]);
            }
        }
        if (notGrantedCount == 0) {
            // all permission granted
            if (callback != null) {
                callback.onGetPermission();
            }
        } else {
            ActivityCompat.requestPermissions(activity,
                    notGranted.toArray(new String[0]), Permission.REQUEST_PERMISSIONS);
        }
    }

    public static boolean hasPermissions(Activity activity, @PermissionCode int...permissions) {
        // map permission code
        int length = permissions.length;
        String[] standardPermissions = new String[length];
        for (int i=0; i<length; i++) {
            standardPermissions[i] = Permission.map(permissions[i]);
        }
        // check permissions
        int notGrantedCount = 0;
        for (int i=0; i<length; i++) {
            if (ContextCompat.checkSelfPermission(activity, standardPermissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                notGrantedCount++;
            }
        }
        return notGrantedCount == 0;
    }

    public static void toSetPermission(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", UtilsApp.getApp().getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}