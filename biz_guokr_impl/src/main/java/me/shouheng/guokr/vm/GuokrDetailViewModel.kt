package me.shouheng.guokr.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.guokr.api.bean.GuokrNewsContent
import me.shouheng.guokr.api.GuokrService
import me.shouheng.guokr.api.OnGetResultListener
import me.shouheng.vmlib.base.BaseViewModel

/**
 * Guokr detail viewmodel.
 *
 * @Author wangshouheng
 * @Time 2021/9/30
 */
class GuokrDetailViewModel(application: Application) : BaseViewModel(application) {

    private val guokrService: GuokrService = ARouter.getInstance().navigation(GuokrService::class.java)

    /** Guokr news id. */
    var newsId: Int = 0

    /** Get guokr news content. */
    fun getNewsDetail() {
        guokrService.getGuokrNewsContent(newsId, object : OnGetResultListener<GuokrNewsContent> {
            override fun onSuccess(data: GuokrNewsContent) {
                setSuccess(GuokrNewsContent::class.java, data)
            }

            override fun onFailed(code: String, msg: String) {
                setFailure(GuokrNewsContent::class.java, code, msg)
            }
        })
    }
}