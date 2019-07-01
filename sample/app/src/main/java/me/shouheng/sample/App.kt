package me.shouheng.sample

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.mvvm.MVVMs

/**
 * Application.
 *
 * @author WngShhng 2019-6-29
 */
class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MVVMs.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        MVVMs.onCreate(this)
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}