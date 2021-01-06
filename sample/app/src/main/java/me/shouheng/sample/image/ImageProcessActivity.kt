package me.shouheng.sample.image

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import me.shouheng.compress.Compress
import me.shouheng.compress.strategy.Strategies
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityProcessBinding
import me.shouheng.uix.image.proc.ImageProcessor
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class ImageProcessActivity : CommonActivity<EmptyViewModel, ActivityProcessBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_process

    override fun doCreateView(savedInstanceState: Bundle?) {
        AsyncTask.execute {
            val uri = intent.getParcelableExtra<Uri>("image")
            val ins = contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options()
            try {
                val source = BitmapFactory.decodeStream(ins, null, options)
                val bitmap = Compress.with(this, source)
                        .strategy(Strategies.compressor())
                        .setMaxHeight(200f)
                        .setMaxWidth(200f)
                        .asBitmap()
                        .get()
                val bin = ImageProcessor.binarizationProcess(bitmap)
                val gray = ImageProcessor.greyProcess(bitmap)
                Handler(Looper.getMainLooper()).post {
                    binding.binary.setImageBitmap(bin)
                    binding.gray.setImageBitmap(gray)
                }
            } catch (ex: Throwable) {
                toast("图片太大了")
            }
        }
    }
}