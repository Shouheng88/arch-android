package me.shouheng.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.home.view.HomeActivity
import me.shouheng.scheduler.ISchedulerJob
import me.shouheng.startup.annotation.StartupJob
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.comn.ContainerActivity

/**
 * Home startup job.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@StartupJob
class HomeInitJob : ISchedulerJob {

    /** This initialize job has the highest priority. */
    override fun priority(): Int = 100

    @SuppressLint("MissingPermission")
    override fun run(context: Context) {
        L.d(">>> HomeInitJob called <<<")
        ContainerActivity.registerCommandHandler { activity, command, containerLayoutId, _ ->
            if (command == HomeActivity.HOME_COMMAND_LAUNCH_GUOKR) {
                val fragment = ARouter.getInstance().build("/guokr/entrance").navigation() as Fragment
                activity.supportFragmentManager.beginTransaction()
                    .replace(containerLayoutId, fragment)
                    .commit()
            }
        }
    }
}
