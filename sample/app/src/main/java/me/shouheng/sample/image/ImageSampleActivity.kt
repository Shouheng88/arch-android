package me.shouheng.sample.image

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy
import me.shouheng.sample.R
import me.shouheng.sample.comn.PalmUtils
import me.shouheng.sample.databinding.ActivityMatisseSampleBinding
import me.shouheng.uix.pages.image.GalleryActivity
import me.shouheng.uix.pages.image.GifSizeFilter
import me.shouheng.uix.pages.image.Glide4Engine
import me.shouheng.uix.pages.image.TakePhotoActivity
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.permission.PermissionUtils
import me.shouheng.utils.permission.callback.OnGetPermissionCallback
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.FileUtils
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel
import java.io.File

@Route(path = "/app/image")
@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class ImageSampleActivity : CommonActivity<EmptyViewModel, ActivityMatisseSampleBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_matisse_sample

    override fun doCreateView(savedInstanceState: Bundle?) { /* noop */ }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x0001 && resultCode == Activity.RESULT_OK) {
            val urls = Matisse.obtainResult(data)
            // 增加一个网络图片显示实例
            urls.add(Uri.parse("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"))
            GalleryActivity.launch(context, ArrayList(urls), 0)
        } else if (requestCode == REQ_CAMERA_SINGLE_MODE && resultCode == Activity.RESULT_OK) {
            L.d("Paths: ${TakePhotoActivity.obtainPathResult(data!!)}")
            val url = TakePhotoActivity.obtainResult(data) { path -> PalmUtils.getUriFromFile(this, File(path)) }[0]
            ActivityUtils.open(GalleryActivity::class.java)
                    .put(GalleryActivity.EXTRA_GALLERY_IMAGES, ArrayList<Uri>(listOf(url)))
                    .launch(this)
        } else if (requestCode == 0x0003 && resultCode == Activity.RESULT_OK) {
            val url = Matisse.obtainResult(data)[0]
            startActivity(Intent(this, CropActivity::class.java).putExtra("image", url))
        } else if (requestCode == 0x0004 && resultCode == Activity.RESULT_OK) {
            val url = Matisse.obtainResult(data)[0]
            startActivity(Intent(this, ImageProcessActivity::class.java).putExtra("image", url))
        } else if (requestCode == REQ_CAMERA_MULTIPLE_MODE && resultCode == Activity.RESULT_OK) {
            L.d("Paths: ${TakePhotoActivity.obtainPathResult(data!!)}, From album: ${TakePhotoActivity.isFromAlbum(data)}")
            val uris = TakePhotoActivity.obtainResult(data) { path -> PalmUtils.getUriFromFile(this, File(path)) }
            GalleryActivity.launch(context, ArrayList(uris), 0)
        }
    }

    fun onMatisseLight(v: View) {
        Matisse.from(this@ImageSampleActivity)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider", "test"))
                .maxSelectable(9)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                // 如果指定了 gridExpectedSize，将优先使用 gridExpectedSize 而不是 spanCount
                .spanCount(4)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                // .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(Glide4Engine())    // for glide-V4
                .setOnSelectedListener { _, pathList ->
                    // DO SOMETHING IMMEDIATELY HERE
                    L.e("onSelected", "onSelected: pathList=$pathList")
                }
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener { isChecked ->
                    // DO SOMETHING IMMEDIATELY HERE
                    L.e("isChecked", "onCheck: isChecked=$isChecked")
                }
                .forResult(0x0001)
    }

    fun onMatisseDark(v: View) {
        Matisse.from(this@ImageSampleActivity)
                .choose(MimeType.ofAll())
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .maxSelectable(9)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(Glide4Engine())
                .forResult(0x0001)
    }

    fun onGallery(v: View) {
        onMatisseDark(v)
    }

    @SuppressLint("MissingPermission")
    fun onPhoto(v: View) {
        // sample for custom camera
        // TakePhotoActivity.showSwitch = false
        val appDir = PathUtils.getExternalAppPicturesPath()
        FileUtils.createOrExistsDir(appDir)
        val fileName = "${System.currentTimeMillis()}.jpg"
        val filePath = File(appDir, fileName).path
        PermissionUtils.checkPermissions(this, OnGetPermissionCallback {
            TakePhotoActivity.launch(this@ImageSampleActivity, filePath, REQ_CAMERA_SINGLE_MODE)
        }, Permission.STORAGE, Permission.CAMERA)
    }

    @SuppressLint("MissingPermission")
    fun onMultiple(v: View) {
        val appDir = PathUtils.getExternalAppPicturesPath()
        FileUtils.createOrExistsDir(appDir)
        PermissionUtils.checkPermissions(this, OnGetPermissionCallback {
            TakePhotoActivity.open()
                    .setFileDir(appDir)
                    .setShowFlash(true)
                    .setShowRay(true)
                    .setShowGrid(true)
                    .build().launch(activity, REQ_CAMERA_MULTIPLE_MODE)
        }, Permission.STORAGE, Permission.CAMERA)
    }

    fun onCrop(v: View) {
        Matisse.from(this@ImageSampleActivity)
                .choose(MimeType.ofImage())
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .maxSelectable(1)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(Glide4Engine())
                .forResult(0x0003)
    }

    fun onProcess(v: View) {
        Matisse.from(this@ImageSampleActivity)
                .choose(MimeType.ofImage())
                .countable(true)
                .theme(R.style.Matisse_Dracula)
                .addFilter(GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .maxSelectable(1)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(Glide4Engine())
                .forResult(0x0004)
    }

    companion object {
        const val REQ_CAMERA_SINGLE_MODE            = 0x0002
        const val REQ_CAMERA_MULTIPLE_MODE          = 0x0005
    }
}