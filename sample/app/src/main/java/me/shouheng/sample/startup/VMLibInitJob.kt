package me.shouheng.sample.startup

import android.annotation.SuppressLint
import android.content.Context
import me.shouheng.sample.R
import me.shouheng.sample.view.MainActivity
import me.shouheng.scheduler.ISchedulerJob
import me.shouheng.startup.annotation.StartupJob
import me.shouheng.uix.pages.crash.CrashReportActivity
import me.shouheng.uix.widget.button.NormalButton
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.CrashHelper
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import java.io.File

/**
 * VMLib startup job.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@StartupJob class VMLibInitJob : ISchedulerJob {

    /** This initialize job has the highest priority. */
    override fun priority(): Int = 100

    @SuppressLint("MissingPermission")
    override fun run(context: Context) {
        L.d(">>> VMLibInitJob called <<<")
        // Config crash reporter.
        CrashHelper.init(context, File(PathUtils.getExternalAppFilesPath(), "crash")) { crashInfo, _ ->
            CrashReportActivity.Companion.Builder(context)
                .setTitle("Oops, crashed!")
                .setContent("Please report this issue to the manger.")
                .setImage(R.drawable.uix_crash_error_image)
                .setMessage(crashInfo)
                .setRestartActivity(MainActivity::class.java)
                .launch()
        }
        // Custom UIX library.
        NormalButton.GlobalConfig.normalColor = ResUtils.getColor(R.color.cold_theme_accent)
    }
}
