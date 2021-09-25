package me.shouheng.eyepetizer.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.eyepetizer.EyepetizerService
import me.shouheng.api.eyepetizer.OnGetHomeBeansListener
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.base.BaseViewModel

/**
 * Eyepetizer viewmodel.
 *
 * @author ShouhengWang (shouheng2020@gmail.com)
 * @version 2019/7/6 18:19
 */
class EyepetizerViewModel(application: Application) : BaseViewModel(application) {

    private var firstPageRequested: Boolean = false

    /** Micro service styled method invocation of [ARouter]. */
    private val eyeService: EyepetizerService = ARouter.getInstance().navigation(EyepetizerService::class.java)

    private var nextPageUrl: String? = null

    /** Request the first page. */
    fun firstPage() {
        if (firstPageRequested) return
        firstPageRequested = true
        setLoading(HomeBean::class.java)
        eyeService.getFirstHomePage(null, object : OnGetHomeBeansListener {
            override fun onGetHomeBean(data: HomeBean) {
                L.d("Got home bean")
                nextPageUrl = data.nextPageUrl
                setSuccess(HomeBean::class.java, data, udf3 = false)
                // request next page
                nextPage()
            }

            override fun onError(code: String, msg: String) {
                L.d("Got home bean error")
                setFailed(HomeBean::class.java, code, msg)
            }
        })
    }

    /** Request the next page. */
    fun nextPage() {
        setLoading(HomeBean::class.java, udf3 = true)
        eyeService.getMoreHomePage(nextPageUrl, object : OnGetHomeBeansListener {
            override fun onGetHomeBean(data: HomeBean) {
                L.d("Got next page home bean")
                nextPageUrl = data.nextPageUrl
                setSuccess(HomeBean::class.java, data, udf3 = true)
            }

            override fun onError(code: String, msg: String) {
                L.d("Got next page home bean error")
                setFailed(HomeBean::class.java, code, msg, udf3 = true)
            }
        })
    }

}