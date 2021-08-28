package me.shouheng.eyepetizer.vm

import android.app.Application
import me.shouheng.api.bean.Item
import me.shouheng.vmlib.base.BaseViewModel

/**
 * @author WngShhng (shouheng2020@gmail.com)
 * @version 2019/7/7 14:19
 */
class VideoDetailsViewModel(application: Application) : BaseViewModel(application) {

    var item: Item? = null

}