package me.shouheng.sample

import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.utils.UtilsApp

/**
 * @author WngShhng 2019-6-29
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        // custom ARouter
        customARouter()
        UtilsApp.init(this)
    }

    private fun customARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}