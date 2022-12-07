package me.shouheng.eyepetizer

import android.content.Context
import me.shouheng.scheduler.ISchedulerJob
import me.shouheng.startup.annotation.StartupJob
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.exception.DefaultExceptionHandler
import me.shouheng.vmlib.exception.GlobalExceptionManager

/**
 * Startup job for eyepetizer module.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@StartupJob
class EyeStartupJob : ISchedulerJob {

    override fun run(context: Context) {
        L.d(">>> EyeStartupJob called <<<")
        // Network
        GlobalExceptionManager.registerExceptionHandler(DefaultExceptionHandler)
    }
}