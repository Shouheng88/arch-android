package me.shouheng.eyepetizer.view

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.shouheng.eyepetizer.databinding.EyepetizerActivityVideoDetailsBinding
import me.shouheng.eyepetizer.R
import me.shouheng.eyepetizer.vm.VideoDetailsViewModel
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ui.ImageUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.ViewBindingActivity

/** Video details activity for playing a video @author Shouheng Wang */
@Route(path = "/eyepetizer/details")
@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class VideoDetailActivity : ViewBindingActivity<VideoDetailsViewModel, EyepetizerActivityVideoDetailsBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        initData()
        initView()
    }

    private fun initData() {
        val itemId = intent?.getIntExtra(EXTRA_ITEM_DATA_ID, 0)?:0
        vm.requestItemById(itemId)
    }

    private fun initView() {
        binding.gsyPlayer.setUp(vm.item!!.data?.playUrl, false, null, null)
        binding.gsyPlayer.setIsTouchWiget(true)
        binding.gsyPlayer.isRotateViewAuto = false
        binding.gsyPlayer.isShowFullAnimation = false
        binding.gsyPlayer.isLockLand = false
        binding.gsyPlayer.isNeedLockFull = true
        binding.gsyPlayer.fullscreenButton.onDebouncedClick {
            binding.gsyPlayer.startWindowFullscreen(this, true, true);
        }
        binding.gsyPlayer.backButton.onDebouncedClick {
            onBackPressed()
        }

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.gsyPlayer.setThumbImageView(imageView)

        Glide.with(this)
            .asBitmap()
            .load(vm.item!!.data?.cover?.homepage)
            .thumbnail(Glide.with(this).asBitmap().load(R.drawable.eyepetizer_card_bg_unlike))
            .addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean = true

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageBitmap(resource)
                    GlobalScope.launch(Dispatchers.Main) {
                        val res = withContext(Dispatchers.IO) {
                            ImageUtils.fastBlur(resource, 1f, 25f)
                        }
                        binding.ivBg.setImageBitmap(res)
                    }
                    return true
                }
            }).into(imageView)
    }

    override fun onBackPressed() {
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
    }

    companion object {
        const val EXTRA_ITEM_DATA_ID = "video_details_item"
    }
}