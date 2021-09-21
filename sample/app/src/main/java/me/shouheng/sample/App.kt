package me.shouheng.sample

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.service.net.Server
import me.shouheng.sample.view.DebugActivity
import me.shouheng.uix.pages.CrashReportActivity
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.BuildConfig
import me.shouheng.vmlib.VMLib
import java.io.File

/**
 * todo dialog activity
 *
 * @author ShouhengWang 2019-6-29
 */
class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Debug.startMethodTracingSampling("trace_log", /*byte*/8*1024*1024, /*ms*/200)
//        } else {
//            Debug.startMethodTracing("trace_log")
//        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize mvvms
        VMLib.onCreate(this)
        // custom L, must be called after MVVMs.onCreate()
        customLog()
        // custom ARouter
        customARouter()
        // custom crash
        customCrash()
        // custom UIX
        customUIX()
        // init kaiyan app server host
        Server.initServer("http://baobab.kaiyanapp.com/api/")
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

    @SuppressLint("MissingPermission")
    private fun customCrash() {
        CrashHelper.init(this,
            File(PathUtils.getExternalAppFilesPath(), "crash")
        ) { crashInfo, _ ->
            CrashReportActivity.Companion.Builder(this)
                .setTitle("Oops, crashed!")
                .setContent("Please the manger to report this issue.")
                .setImage(R.drawable.uix_crash_error_image)
                .setMessage(crashInfo)
                .setRestartActivity(DebugActivity::class.java)
                .launch()
        }
    }

    private fun customUIX() {
        NormalButton.GlobalConfig.normalColor = ResUtils.getColor(R.color.cold_theme_accent)
    }
}