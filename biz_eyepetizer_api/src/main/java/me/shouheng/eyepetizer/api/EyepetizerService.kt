package me.shouheng.eyepetizer.api

import com.alibaba.android.arouter.facade.template.IProvider
import me.shouheng.eyepetizer.api.bean.HomeBean
import me.shouheng.eyepetizer.api.bean.Item

/** Service to get eyepetizer data */
interface EyepetizerService : IProvider {

    /** Request home page first page data */
    fun getFirstHomePage(date: Long?, listener: OnGetHomeBeansListener)

    /** Request more data from left pages */
    fun getMoreHomePage(url: String?, listener: OnGetHomeBeansListener)

    /** Get home bean item by id. */
    fun getItemById(itemId: Int): Item?
}

/** Eyepetizer result callback. */
interface OnGetHomeBeansListener {

    /** Called when got first page home bean data */
    fun onGetHomeBean(data: HomeBean)

    /** The error callback */
    fun onError(code: String, msg: String)
}
