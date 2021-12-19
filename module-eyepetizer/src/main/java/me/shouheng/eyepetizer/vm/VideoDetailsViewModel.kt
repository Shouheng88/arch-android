package me.shouheng.eyepetizer.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.Item
import me.shouheng.api.service.EyepetizerService
import me.shouheng.vmlib.base.BaseViewModel

/**
 * Viewmodel for Eyepetizer video details.
 *
 * @author ShouhengWang (shouheng2020@gmail.com)
 * @version 2019/7/7 14:19
 */
class VideoDetailsViewModel(application: Application) : BaseViewModel(application) {

    /** Micro service styled method invocation of [ARouter]. */
    private val eyeService: EyepetizerService = ARouter.getInstance().navigation(EyepetizerService::class.java)

    var item: Item? = null

    /** Request item object by its id. */
    fun requestItemById(itemId: Int) {
        item = eyeService.getItemById(itemId)
    }
}
