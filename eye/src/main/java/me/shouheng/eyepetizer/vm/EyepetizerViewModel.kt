package me.shouheng.eyepetizer.vm

import android.app.Application
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.eyepetizer.EyepetizerService
import me.shouheng.api.eyepetizer.OnGetHomeBeansListener
import me.shouheng.api.sample.UserService
import me.shouheng.mvvm.base.BaseViewModel
import me.shouheng.mvvm.bean.Resources

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 18:19
 */
class EyepetizerViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var userService: UserService

    private lateinit var eyepetizerService: EyepetizerService

    private var nextPageUrl: String? = null

    override fun onCreate(extras: Bundle?, savedInstanceState: Bundle?) {
        super.onCreate(extras, savedInstanceState)
        userService = ARouter.getInstance().navigation(UserService::class.java)
        eyepetizerService = ARouter.getInstance().navigation(EyepetizerService::class.java)
    }

    fun requestUser() {
        userService.requestUser()
    }

    fun requestFirstPage() {
        getObservable(HomeBean::class.java).value = Resources.loading()
        eyepetizerService.getFirstHomePage(null, object : OnGetHomeBeansListener {
            override fun onError(errorCode: String, errorMsg: String) {
                getObservable(HomeBean::class.java).value = Resources.failed(errorCode, errorMsg)
            }

            override fun onGetHomeBean(homeBean: HomeBean) {
                nextPageUrl = homeBean.nextPageUrl
                getObservable(HomeBean::class.java).value = Resources.success(homeBean)
            }
        })
    }

    fun requestNextPage() {
        eyepetizerService.getMoreHomePage(nextPageUrl, object : OnGetHomeBeansListener {
            override fun onError(errorCode: String, errorMsg: String) {
                getObservable(HomeBean::class.java).value = Resources.failed(errorCode, errorMsg)
            }

            override fun onGetHomeBean(homeBean: HomeBean) {
                nextPageUrl = homeBean.nextPageUrl
                getObservable(HomeBean::class.java).value = Resources.success(homeBean)
            }
        })
    }
}