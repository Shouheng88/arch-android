package me.shouheng.sample

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.mvvm.MVVMs
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.LogUtils

/**
 * @author WngShhng 2019-6-29
 */
class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        // initialize mvvms
        MVVMs.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        // initialize mvvms
        MVVMs.onCreate(this)

        // custom LogUtils, must be called after MVVMs.onCreate()
        customLog()

        // custom ARouter
        customARouter()

        // custom crash
        customCrash()
    }

    private fun customLog() {
        LogUtils.getConfig()
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
            CrashHelper.init(this, "") { crashInfo, e ->
                LogUtils.e(crashInfo)
                LogUtils.e(e)
            }
        }
    }
}