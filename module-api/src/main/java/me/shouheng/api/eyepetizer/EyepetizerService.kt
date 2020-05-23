package me.shouheng.api.eyepetizer

import com.alibaba.android.arouter.facade.template.IProvider
import me.shouheng.api.bean.HomeBean

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 17:56
 */
interface EyepetizerService : IProvider {

    /**
     * 获取主页的数据
     *
     * @param onGetHomeBeansListener 回调
     */
    fun getFirstHomePage(date: Long?, onGetHomeBeansListener: OnGetHomeBeansListener)

    /**
     * 获取更多主页的数据
     *
     * @param onGetHomeBeansListener 回调
     */
    fun getMoreHomePage(url: String?, onGetHomeBeansListener: OnGetHomeBeansListener)
}

/**
 * 获取主页数据的回调接口
 */
interface OnGetHomeBeansListener {

    /**
     * 当获取到主页数据的时候回调的方法
     *
     * @param homeBean 主页数据
     */
    fun onGetHomeBean(homeBean: HomeBean)

    /**
     * 错误信息回调
     */
    fun onError(errorCode: String, errorMsg: String)
}