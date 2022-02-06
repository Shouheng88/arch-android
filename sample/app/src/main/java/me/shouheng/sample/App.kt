package me.shouheng.sample

import android.content.Context
import androidx.multidex.MultiDexApplication
import kotlinx.coroutines.*
import me.shouheng.startup.launchStartup
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.VMLib

/** App for VMLib sample.  @author ShouhengWang 2019-6-29 */
class App : MultiDexApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        VMLib.onCreate(this)
        launchStartup(this) {
            scanAnnotations()
        }
        ktTest2()
    }

    private fun ktTest() {
        GlobalScope.launch(Dispatchers.Main) {
            L.d("1")
            val a = withContext(Dispatchers.IO) {
                L.d("2")
                Thread.sleep(2_000)
                L.d("3")
                "A"
            }
            L.d("4")
            val b = withContext(Dispatchers.IO) {
                L.d("5")
                Thread.sleep(2_000)
                L.d("6")
                "B"
            }
            L.d("7")
            toast("1:$a 2:$b")
        } // run as: 1, 2, 3, 4, 5, 6, 7
    }

    private fun ktTest2() {
        GlobalScope.launch(Dispatchers.Main) {
            L.d("1")
            val pair = withContext(Dispatchers.IO) {
                L.d("2")
                val a = async {
                    L.d("3")
                    val ret = withContext(Dispatchers.IO) {
                        L.d("4")
                        Thread.sleep(2_000)
                        L.d("5")
                        "A"
                    }
                    L.d("6")
                    ret
                }
                L.d("7")
                val b = async {
                    L.d("8")
                    val ret = withContext(Dispatchers.IO) {
                        L.d("9")
                        Thread.sleep(2_000)
                        L.d("10")
                        "B"
                    }
                    L.d("11")
                    ret
                }
                L.d("12")
                Pair(a.await(), b.await())
            }
            L.d("13")
            toast("1:${pair.first} 2:${pair.second}")
        } // run as: 1, 2, 3, 4, 5, 6, 7
    }
}
