package me.shouheng.sample.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.GuokrNews
import me.shouheng.api.bean.Result
import me.shouheng.api.service.GuokrService
import me.shouheng.api.service.OnGetResultListener
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.base.BaseViewModel

/**
 * Guokr viewmodel
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
class GuokrViewModel(application: Application) : BaseViewModel(application) {

    private val guokrService: GuokrService = ARouter.getInstance().navigation(GuokrService::class.java)

    private var offset: Int = 0
    private val limit: Int = 20

    val news = mutableListOf<Result>()

    /** Get guokr news */
    fun getNews(fid: String) {
        L.d("Requesting guokr news of offset[$offset] and limit[$limit] for fid[$fid].")
        guokrService.getGuokrNews(offset, limit, object : OnGetResultListener<GuokrNews> {
            override fun onSuccess(data: GuokrNews) {
                offset += limit
                val ret = data.result?: emptyList()
                news.addAll(ret)
                setListSuccess(Result::class.java, ret)
            }

            override fun onFailed(code: String, msg: String) {
                setListFailure(Result::class.java, code, msg)
            }
        })
    }
}