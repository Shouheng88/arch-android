package me.shouheng.common.startup

import android.content.Context
import me.shouheng.scheduler.ISchedulerJob
import me.shouheng.startup.annotation.StartupJob
import me.shouheng.utils.stability.L

/**
 * Api module startup job.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@StartupJob
class EyepetizerApiStartupJob : ISchedulerJob {

    override fun run(context: Context) {
        L.d(">>> ApiStartupJob called <<<")
    }
}
