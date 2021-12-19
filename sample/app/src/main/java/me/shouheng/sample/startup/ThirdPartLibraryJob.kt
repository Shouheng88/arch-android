package me.shouheng.sample.startup

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.scheduler.ISchedulerJob
import me.shouheng.startup.annotation.StartupJob
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.BuildConfig

/**
 * Third part library initialize job.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@StartupJob class ThirdPartLibraryJob : ISchedulerJob {

    override fun run(context: Context) {
        L.d(">>> ThirdPartLibraryJob called <<<")
        // Config log
        L.getConfig().setLogSwitch(true)
            .setLogHeadSwitch(true)
            .setBorderSwitch(true)
            .setConsoleSwitch(true)
        // Config for ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(context as Application)
    }
}