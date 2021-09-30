package me.shouheng.api.service

import com.alibaba.android.arouter.facade.template.IProvider
import me.shouheng.api.bean.GuokrNews
import me.shouheng.api.bean.GuokrNewsContent

/** Guokr remote service for component development. */
interface GuokrService : IProvider {

    /** Get guokr news. */
    fun getGuokrNews(offset: Int, limit: Int, callback: OnGetResultListener<GuokrNews>)

    /** Get guokr news content. */
    fun getGuokrNewsContent(id: Int, callback: OnGetResultListener<GuokrNewsContent>)
}

/** Reused result callback. */
interface OnGetResultListener<T> {

    /** Called when get the result. */
    fun onSuccess(data: T)

    /** Called when failed to get the result. */
    fun onFailed(code: String, msg: String)
}
