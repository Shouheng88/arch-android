package me.shouheng.sample.image

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityCropBinding
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.vmlib.anno.ActivityConfiguration

import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class CropActivity : CommonActivity<EmptyViewModel, ActivityCropBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_crop

    override fun doCreateView(savedInstanceState: Bundle?) {
        val uri = intent.getParcelableExtra<Uri>("image")
        val ins = contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options()
        try {
            val map = BitmapFactory.decodeStream(ins, null, options)
            binding.civ.setImageToCrop(map, null)
        } catch (ex: Throwable) {
            toast("图片太大了")
        }
    }

    fun onConfirm(v: View) {
        val ret = binding.civ.crop()
        binding.civ.setImageToCrop(ret, null)
    }
}
