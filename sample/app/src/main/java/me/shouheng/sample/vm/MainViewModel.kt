package me.shouheng.sample.vm

import android.app.Application
import me.shouheng.mvvm.base.CommonViewModel
import me.shouheng.mvvm.data.Resources
import me.shouheng.sample.async.InnerAsync

class MainViewModel(application: Application) : CommonViewModel(application) {

    fun startLoad() {
        getObservable(String::class.java).value = Resources.loading("loading")
        InnerAsync(object : InnerAsync.OnFinish {
            override fun onFinish() {
                getObservable(String::class.java).value = Resources.success("succeed")
            }
        }).execute()
    }

}