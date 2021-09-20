package me.shouheng.sample.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.sample.MainDataService
import me.shouheng.api.sample.OnGetMainDataListener
import me.shouheng.vmlib.base.BaseViewModel
import me.shouheng.vmlib.bean.Resources

/**
 * 主界面对应的 ViewModel
 *
 * @author ShouhengWang 2019-07-05
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    val liveData = MutableLiveData<String>()

    val resLiveData = MutableLiveData<Resources<String>>()

    fun startLoad() {
        setLoading(String::class.java)
        ARouter.getInstance().navigation(MainDataService::class.java)
            ?.loadData(object : OnGetMainDataListener{
                override fun onGetData(data: String) {
                    setLoading(String::class.java)
                }
            })
    }

}