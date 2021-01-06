package me.shouheng.api.eyepetizer

import com.alibaba.android.arouter.facade.template.IProvider
import me.shouheng.api.bean.HomeBean

/** Service to get eyepetizer data */
interface EyepetizerService : IProvider {

    /** Request home page first page data */
    fun getFirstHomePage(date: Long?, onGetHomeBeansListener: OnGetHomeBeansListener)

    /** Request more data from left pages */
    fun getMoreHomePage(url: String?, onGetHomeBeansListener: OnGetHomeBeansListener)
}

interface OnGetHomeBeansListener {

    /** Called when got first page home bean data */
    fun onGetHomeBean(homeBean: HomeBean)

    /** The error callback */
    fun onError(errorCode: String, errorMsg: String)
}
