package me.shouheng.eyepetizer.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.eyepetizer.EyepetizerService
import me.shouheng.api.eyepetizer.OnGetHomeBeansListener
import me.shouheng.api.sample.UserService
import me.shouheng.vmlib.base.BaseViewModel

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 18:19
 */
class EyepetizerViewModel(application: Application) : BaseViewModel(application) {

    private val userService: UserService = ARouter.getInstance().navigation(UserService::class.java)

    private val eyeService: EyepetizerService = ARouter.getInstance().navigation(EyepetizerService::class.java)

    private var nextPageUrl: String? = null

    fun requestUser() { userService.requestUser() }

    fun firstPage() {
        setLoading(HomeBean::class.java)
        eyeService.getFirstHomePage(null, object : OnGetHomeBeansListener {
            override fun onGetHomeBean(data: HomeBean) {
                nextPageUrl = data.nextPageUrl
                setSuccess(HomeBean::class.java, data)
                // request next page
                nextPage()
            }

            override fun onError(code: String, msg: String) {
                setFailed(HomeBean::class.java, code, msg)
            }
        })
    }

    fun nextPage() {
        eyeService.getMoreHomePage(nextPageUrl, object : OnGetHomeBeansListener {
            override fun onGetHomeBean(data: HomeBean) {
                nextPageUrl = data.nextPageUrl
                setSuccess(HomeBean::class.java, data)
            }

            override fun onError(code: String, msg: String) {
                setFailed(HomeBean::class.java, code, msg)
            }
        })
    }
}