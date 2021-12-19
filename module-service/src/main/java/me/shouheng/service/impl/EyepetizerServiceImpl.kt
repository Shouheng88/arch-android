package me.shouheng.service.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.api.bean.Item
import me.shouheng.api.service.EyepetizerService
import me.shouheng.api.service.OnGetHomeBeansListener
import me.shouheng.service.repo.EyeRepo
import me.shouheng.utils.stability.L

/**
 * Micro service for eyepetizer.
 *
 * @author ShouhengWang (shouheng2020@gmail.com)
 * @version 2019/7/6 18:00
 */
@Route(path = "/api/eye")
class EyepetizerServiceImpl : EyepetizerService {

    override fun getFirstHomePage(date: Long?, listener: OnGetHomeBeansListener) {
        EyeRepo.INSTANCE.firstPage(date, {
            listener.onGetHomeBean(it)
        }, { code, msg ->
            listener.onError(code, msg)
        })
    }

    override fun getMoreHomePage(url: String?, listener: OnGetHomeBeansListener) {
        EyeRepo.INSTANCE.morePage(url, {
            listener.onGetHomeBean(it)
        }, { code, msg ->
            listener.onError(code, msg)
        })
    }

    override fun getItemById(itemId: Int): Item? {
        return EyeRepo.INSTANCE.getItemById(itemId)
    }

    override fun init(context: Context?) {
        L.i(EyepetizerServiceImpl::class.java.name + " initialized.")
    }
}