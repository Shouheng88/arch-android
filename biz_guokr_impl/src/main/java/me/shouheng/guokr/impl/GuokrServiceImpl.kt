package me.shouheng.guokr.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.guokr.api.GuokrService
import me.shouheng.guokr.api.bean.GuokrNews
import me.shouheng.guokr.api.bean.GuokrNewsContent
import me.shouheng.guokr.api.OnGetResultListener
import me.shouheng.guokr.repo.GuokrRepo

/**
 * Mirco service for guokr.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@Route(path = "/guokr/api")
class GuokrServiceImpl: GuokrService {

    override fun getGuokrNews(offset: Int, limit: Int, callback: OnGetResultListener<GuokrNews>) {
        GuokrRepo.INSTANCE.getNews(offset, limit, {
            callback.onSuccess(it)
        }, { code, msg ->
            callback.onFailed(code, msg)
        })
    }

    override fun getGuokrNewsContent(id: Int, callback: OnGetResultListener<GuokrNewsContent>) {
        GuokrRepo.INSTANCE.getNewsContent(id, {
            callback.onSuccess(it)
        }, { code, msg ->
            callback.onFailed(code, msg)
        })
    }

    override fun init(context: Context?) {}
}