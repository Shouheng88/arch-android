package me.shouheng.eyepetizer.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.eyepetizer.EyepetizerService
import me.shouheng.api.eyepetizer.OnGetHomeBeansListener
import me.shouheng.api.sample.UserService
import me.shouheng.vmlib.base.BaseViewModel
import me.shouheng.vmlib.bean.Resources

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 18:19
 */
class EyepetizerViewModel(application: Application) : BaseViewModel(application) {

    private var userService: UserService = ARouter.getInstance().navigation(UserService::class.java)

    private var eyepetizerService: EyepetizerService = ARouter.getInstance().navigation(EyepetizerService::class.java)

    private var nextPageUrl: String? = null

    fun requestUser() { userService.requestUser() }

    fun requestFirstPage() {
        getObservable(HomeBean::class.java).value = Resources.loading()
        eyepetizerService.getFirstHomePage(null, object : OnGetHomeBeansListener {
            override fun onError(errorCode: String, errorMsg: String) {
                setFailed(HomeBean::class.java, errorCode, errorMsg)
            }

            override fun onGetHomeBean(homeBean: HomeBean) {
                nextPageUrl = homeBean.nextPageUrl
                setSuccess(HomeBean::class.java, homeBean)
                // 再请求一页
                requestNextPage()
            }
        })
    }

    fun requestNextPage() {
        eyepetizerService.getMoreHomePage(nextPageUrl, object : OnGetHomeBeansListener {
            override fun onError(errorCode: String, errorMsg: String) {
                setFailed(HomeBean::class.java, errorCode, errorMsg)
            }

            override fun onGetHomeBean(homeBean: HomeBean) {
                nextPageUrl = homeBean.nextPageUrl
                setSuccess(HomeBean::class.java, homeBean)
            }
        })
    }
}