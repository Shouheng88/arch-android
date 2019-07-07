package me.shouheng.eyepetizer

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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.shouheng.api.bean.Item
import me.shouheng.eyepetizer.databinding.EyepetizerActivityVideoDetailsBinding
import me.shouheng.eyepetizer.vm.VideoDetailsViewModel
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.utils.stability.LogUtils
import me.shouheng.utils.ui.ImageUtils

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/7 14:16
 */
@Route(path = "/eyepetizer/details")
class VideoDetailActivity : CommonActivity<EyepetizerActivityVideoDetailsBinding, VideoDetailsViewModel>() {

    companion object {
        const val EXTRA_ITEM = "video_details_item"
    }

    override fun getLayoutResId() = R.layout.eyepetizer_activity_video_details

    override fun doCreateView(savedInstanceState: Bundle?) {
        initData()
        initView()
    }

    private fun initData() {
    }

    private fun initView() {
        binding.gsyPlayer.setUp(vm.item.data.playUrl, false, null, null)
        binding.gsyPlayer.setIsTouchWiget(true)
        binding.gsyPlayer.isRotateViewAuto = false
        binding.gsyPlayer.isShowFullAnimation = false
        binding.gsyPlayer.isLockLand = false
        binding.gsyPlayer.isNeedLockFull = true
        binding.gsyPlayer.fullscreenButton.setOnClickListener {
            binding.gsyPlayer.startWindowFullscreen(this, true, true);
        }
        binding.gsyPlayer.backButton.setOnClickListener {
            onBackPressed()
        }

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.gsyPlayer.setThumbImageView(imageView)

        Glide.with(this)
            .asBitmap()
            .load(vm.item.data.cover?.homepage)
            .thumbnail(Glide.with(this).asBitmap().load(R.drawable.recommend_summary_card_bg_unlike))
            .addListener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageBitmap(resource)
                    val d = Observable.create<Bitmap> {
                        it.onNext(ImageUtils.fastBlur(resource, 1f, 25f))
                    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                        binding.ivBg.setImageBitmap(it!!)
                    }, {
                        LogUtils.e(it)
                    })
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
}