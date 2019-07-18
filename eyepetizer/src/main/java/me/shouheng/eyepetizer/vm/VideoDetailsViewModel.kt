package me.shouheng.eyepetizer.vm

import android.app.Application
import android.os.Bundle
import me.shouheng.api.bean.Item
import me.shouheng.eyepetizer.VideoDetailActivity.Companion.EXTRA_ITEM
import me.shouheng.mvvm.base.BaseViewModel

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/7 14:19
 */
class VideoDetailsViewModel(application: Application) : BaseViewModel(application) {

    lateinit var item: Item

    override fun onCreate(extras: Bundle?, savedInstanceState: Bundle?) {
        item = extras?.getSerializable(EXTRA_ITEM) as Item
    }
}