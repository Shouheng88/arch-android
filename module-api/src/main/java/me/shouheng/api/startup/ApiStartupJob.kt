package me.shouheng.api.startup

import android.content.Context
import io.realm.Realm
import me.shouheng.scheduler.ISchedulerJob
import me.shouheng.scheduler.ThreadMode
import me.shouheng.startup.annotation.StartupJob
import me.shouheng.utils.stability.L

/**
 * Api module startup job.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@StartupJob
class ApiStartupJob : ISchedulerJob {

    override fun threadMode(): ThreadMode = ThreadMode.MAIN

    override fun dependencies(): List<Class<out ISchedulerJob>> = emptyList()

    override fun run(context: Context) {
        L.d(">>> ApiStartupJob called <<<")
        // Network
        Realm.init(context)
    }
}