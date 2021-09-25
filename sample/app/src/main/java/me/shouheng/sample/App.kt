package me.shouheng.sample

import android.annotation.SuppressLint
import androidx.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.sample.view.MainActivity
import me.shouheng.service.net.Server
import me.shouheng.uix.pages.crash.CrashReportActivity
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.BuildConfig
import me.shouheng.vmlib.VMLib
import java.io.File

/** App for VMLib sample.  @author ShouhengWang 2019-6-29 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        VMLib.onCreate(this)
        customLog()
        customARouter()
        customCrash()
        customUIX()
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
        CrashHelper.init(this, File(PathUtils.getExternalAppFilesPath(), "crash")) { crashInfo, _ ->
            CrashReportActivity.Companion.Builder(this)
                .setTitle("Oops, crashed!")
                .setContent("Please the manger to report this issue.")
                .setImage(R.drawable.uix_crash_error_image)
                .setMessage(crashInfo)
                .setRestartActivity(MainActivity::class.java)
                .launch()
        }
    }

    private fun customUIX() {
        NormalButton.GlobalConfig.normalColor = ResUtils.getColor(R.color.cold_theme_accent)
    }
}