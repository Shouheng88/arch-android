package me.shouheng.sample

import android.app.Application
import me.shouheng.utils.UtilsApp

/**
 * Application.
 *
 * @author WngShhng 2019-6-29
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UtilsApp.init(this)
    }
}