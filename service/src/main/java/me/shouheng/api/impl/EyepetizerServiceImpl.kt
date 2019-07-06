package me.shouheng.api.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.shouheng.api.eyepetizer.EyepetizerService
import me.shouheng.api.eyepetizer.OnGetHomeBeansListener
import me.shouheng.api.eyeptizer.APIRetrofit
import me.shouheng.utils.stability.LogUtils

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 18:00
 */
@Route(path = "/api/eye")
class EyepetizerServiceImpl : EyepetizerService {

    override fun getFirstHomePage(date: Long?, onGetHomeBeansListener: OnGetHomeBeansListener) {
        val d = APIRetrofit.eyepetizer.getFirstHomeData(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onGetHomeBeansListener.onGetHomeBean(it)
            }, {
                LogUtils.e(it)
            })
    }

    override fun getMoreHomePage(url: String?, onGetHomeBeansListener: OnGetHomeBeansListener) {
        val d = APIRetrofit.eyepetizer.getMoreHomeData(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onGetHomeBeansListener.onGetHomeBean(it)
            }, {
                LogUtils.e(it)
            })
    }

    override fun init(context: Context?) {
    }
}