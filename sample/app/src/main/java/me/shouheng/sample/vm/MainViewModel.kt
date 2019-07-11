package me.shouheng.sample.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.sample.MainDataService
import me.shouheng.api.sample.OnGetMainDataListener
import me.shouheng.mvvm.base.BaseViewModel
import me.shouheng.mvvm.bean.Resources

/**
 * 主界面对应的 ViewModel
 *
 * @author WngShhng 2019-07-05
 */
class MainViewModel(application: Application) : BaseViewModel(application) {

    fun startLoad() {
        getObservable(String::class.java).value = Resources.loading()
        ARouter.getInstance().navigation(MainDataService::class.java)
            ?.loadData(object : OnGetMainDataListener{
                override fun onGetData() {
                    getObservable(String::class.java).value = Resources.loading()
                }
            })
    }

}