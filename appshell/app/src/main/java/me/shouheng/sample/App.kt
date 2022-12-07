package me.shouheng.sample

import android.content.Context
import androidx.multidex.MultiDexApplication
import io.realm.Realm
import me.shouheng.startup.launchStartup
import me.shouheng.vmlib.Arch

/** App for VMLib sample.  @author ShouhengWang 2019-6-29 */
class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Arch.onCreate(this)
        launchStartup(this) {
            scanAnnotations()
        }
        Realm.init(this)
    }
}
