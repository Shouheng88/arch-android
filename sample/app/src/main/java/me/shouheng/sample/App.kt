package me.shouheng.sample

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Debug
import android.support.multidex.MultiDexApplication
import android.support.v4.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.mvvm.BuildConfig
import me.shouheng.mvvm.MVVMs
import me.shouheng.sample.view.MainActivity
import me.shouheng.uix.page.CrashActivity
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import java.io.File

/**
 * @author WngShhng 2019-6-29
 */
class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // initialize mvvms
        MVVMs.attachBaseContext(base)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Debug.startMethodTracingSampling("trace_log", /*byte*/8*1024*1024, /*ms*/200)
        } else {
            Debug.startMethodTracing("trace_log")
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize mvvms
        MVVMs.onCreate(this)
        // custom L, must be called after MVVMs.onCreate()
        customLog()
        // custom ARouter
        customARouter()
        // custom crash
        customCrash()
    }

    private fun customLog() {
        L.getConfig()
            .setLogSwitch(true)
            .setLogHeadSwitch(true)
            .setBorderSwitch(true)
            .setConsoleSwitch(true)
    }

    private fun customARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    private fun customCrash() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            CrashHelper.init(this,
                File(PathUtils.getExternalAppFilesPath(), "crash")
            ) { crashInfo, _ ->
                ActivityUtils.open(CrashActivity::class.java)
                    .put(CrashActivity.EXTRA_KEY_CRASH_INFO, crashInfo)
                    .put(CrashActivity.EXTRA_KEY_CRASH_IMAGE, R.drawable.uix_crash_error_image)
                    .put(CrashActivity.EXTRA_KEY_RESTART_ACTIVITY, MainActivity::class.java)
                    .put(CrashActivity.EXTRA_KEY_CRASH_TIPS, "Oops, crashed!")
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .launch(this)
            }
        }
    }
}