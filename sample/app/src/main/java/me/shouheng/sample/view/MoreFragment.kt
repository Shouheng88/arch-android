package me.shouheng.sample.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMoreBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.permission.Permission
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.CommonFragment
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.comn.EmptyViewModel
import me.shouheng.vmlib.component.post
import me.shouheng.vmlib.network.DownloadListener
import me.shouheng.vmlib.network.Downloader
import java.io.File

/**
 * Sample fragment for more features. This is the fragment extend from [CommonFragment] by databinding.
 *
 * @author ShouhengWang 2019-6-29
 */
@FragmentConfiguration(shareViewModel = true, useEventBus = true)
class MoreFragment : CommonFragment<EmptyViewModel, FragmentMoreBinding>() {

    private val downloadUrl = "https://images.unsplash.com/photo-1501879779179-4576bae71d8" +
            "d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=vladimir-riabinin-diMBLU4FzDQ-unsplash.jpg"

    override fun getLayoutResId(): Int = R.layout.fragment_more

    override fun doCreateView(savedInstanceState: Bundle?) {
        initViews()
    }

    @SuppressLint("MissingPermission")
    private fun initViews() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = stringOf(R.string.main_more_widget)

        binding.btnCheckPermission.onDebouncedClick {
            checkPermission(Permission.CAMERA) {
                toast(R.string.main_more_widget_check_permission_granted)
            }
        }
        binding.btnDownload.onDebouncedClick {
            Downloader.getInstance()
                .setOnlyWifi(true)
                .download(downloadUrl, PathUtils.getExternalStoragePath(), object : DownloadListener {
                    override fun onError(errorCode: Int) {
                        toast("Download : error $errorCode")
                    }

                    override fun onStart() {
                        toast("Download : start")
                    }

                    override fun onProgress(readLength: Long, contentLength: Long) {
                        toast("Download : onProgress $readLength/$contentLength")
                    }

                    override fun onComplete(file: File?) {
                        toast("Download : complete : ${file?.absoluteFile}")
                    }
                })
        }
        binding.btnPref.onDebouncedClick {
            ContainerActivity.openFragment(SamplePreference::class.java)
                .put(ContainerActivity.KEY_EXTRA_THEME_ID, R.style.RedAppTheme)
                .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
                .withDirection(ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
                .launch(requireContext())
        }
        binding.btnCrash.onDebouncedClick { 1/0 }
        binding.btnVmPost.onDebouncedClick {
            post(SimpleEvent(stringOf(R.string.main_more_widget_make_a_post_message)))
        }
    }
}
