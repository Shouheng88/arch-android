package me.shouheng.sample.utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;

import me.shouheng.sample.R;
import me.shouheng.sample.utils.activity.TestActivityHelper;
import me.shouheng.sample.utils.app.TestAppUtilsActivity;
import me.shouheng.sample.utils.common.BaseActivity;
import me.shouheng.sample.utils.common.FileUtils;
import me.shouheng.sample.utils.data.TestEncryptUtilsActivity;
import me.shouheng.sample.utils.data.TestTimeUtilsActivity;
import me.shouheng.sample.utils.device.TestDeviceUtilsActivity;
import me.shouheng.sample.utils.device.TestNetworkUtilsActivity;
import me.shouheng.sample.utils.device.TestShellActivity;
import me.shouheng.sample.utils.intent.TestIntentActivity;
import me.shouheng.sample.utils.permission.TestPermissionActivity;
import me.shouheng.sample.utils.stability.TestCrashActivity;
import me.shouheng.sample.utils.stability.TestLogActivity;
import me.shouheng.sample.utils.store.TestPathUtilsActivity;
import me.shouheng.sample.utils.store.TestSpUtilsActivity;
import me.shouheng.sample.utils.ui.TestImageUtilsActivity;
import me.shouheng.sample.utils.ui.TestToastUtilsActivity;
import me.shouheng.sample.utils.ui.TestViewUtilsActivity;
import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.stability.CrashHelper;
import me.shouheng.utils.stability.CrashHelper.OnCrashListener;
import me.shouheng.utils.stability.LogUtils;
import me.shouheng.utils.permission.PermissionUtils;
import me.shouheng.utils.permission.callback.OnGetPermissionCallback;

/**
 * @author shouh
 * @version $Id: MainActivity, v 0.1 2018/11/22 12:10 shouh Exp$
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utils_main);

        findViewById(R.id.btn_permission).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start(MainActivity.this, TestPermissionActivity.class);
            }
        });
        findViewById(R.id.btn_activity_helper).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start(MainActivity.this, TestActivityHelper.class);
            }
        });
        findViewById(R.id.btn_crash).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start(MainActivity.this, TestCrashActivity.class);
            }
        });
        findViewById(R.id.btn_log).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.start(MainActivity.this, TestLogActivity.class);
            }
        });

        PermissionUtils.checkStoragePermission(this, new OnGetPermissionCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGetPermission() {
                CrashHelper.init(getApplication(), FileUtils.getExternalStoragePublicCrashDir(), new OnCrashListener() {
                    @Override
                    public void onCrash(String crashInfo, Throwable e) {
                        LogUtils.e(crashInfo);
                    }
                });
                LogUtils.getConfig()
                        .setLog2FileSwitch(true)
                        .setDir(FileUtils.getExternalStoragePublicLogDir())
                        .setFileFilter(LogUtils.W);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d("STOP MAIN");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d("PAUSE MAIN");
    }

    public void testIntentUtils(View view) {
        ActivityUtils.start(this, TestIntentActivity.class);
    }

    public void testShellUtils(View view) {
        ActivityUtils.start(this, TestShellActivity.class);
    }

    public void testAppUtils(View v) {
        ActivityUtils.start(this, TestAppUtilsActivity.class);
    }

    public void testSPUtils(View v) {
        ActivityUtils.start(this, TestSpUtilsActivity.class);
    }

    public void testDeviceUtils(View v) {
        ActivityUtils.start(this, TestDeviceUtilsActivity.class);
    }

    public void testNetworkUtils(View v) {
        ActivityUtils.start(this, TestNetworkUtilsActivity.class);
    }

    public void testPathUtils(View v) {
        ActivityUtils.start(this, TestPathUtilsActivity.class);
    }

    public void testImageUtils(View v) {
        ActivityUtils.start(this, TestImageUtilsActivity.class);
    }

    public void testViewUtils(View view) {
        ActivityUtils.start(this, TestViewUtilsActivity.class);
    }

    public void testEncryptUtils(View view) {
        ActivityUtils.start(this, TestEncryptUtilsActivity.class);
    }

    public void testTimeUtils(View view) {
        ActivityUtils.start(this, TestTimeUtilsActivity.class);
    }

    public void testToastUtils(View view) {
        ActivityUtils.start(this, TestToastUtilsActivity.class);
    }
}
