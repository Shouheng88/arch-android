package me.shouheng.sample.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.base.BaseViewModel
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.component.execute

/**
 * More options viewmodel.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
class OptionsViewModel(application: Application) : BaseViewModel(application) {

    val longTaskLiveData = MutableLiveData<Resources<String>>()

    val bindLiveData = MutableLiveData<Resources<Int>>()

    /**
     * Do a long time task. This is used to show sample for viewmodel scoped
     * task [execute] logic. The task is bound with viewmodel lifecycle. If the
     * viewmodel is cleared, then the job will be canceled.
     */
    fun doLongTask() {
        execute<String> {
            task {
                L.d("OptionsViewModel run in ${Thread.currentThread()}")
                delay(5_000)
                "Bingo!!"
            }
            // By default the task run in main thread (viewmodel scope). We can change the
            // execution scope by calling 'executeOn' method.
            // executeOn(Dispatchers.IO)
            bind(longTaskLiveData)
            onSucceed {
                toast(it.data)
            }
        }
    }

    fun doTickTick() {
        execute<Int> {
            task {
                for (i in 1..9) {
                    publishProgress(i)
                    delay(1_000)
                }
                10
            }
            bind(bindLiveData)
        }
    }
}
