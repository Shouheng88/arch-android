package me.shouheng.sample.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.bean.Status
import me.shouheng.mvvm.common.ContainerActivity
import me.shouheng.mvvm.http.DownloadListener
import me.shouheng.mvvm.http.Downloader
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.LogUtils
import me.shouheng.utils.store.PathUtils
import me.shouheng.utils.ui.ToastUtils
import me.shouheng.utils.ui.ViewUtils
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.lang.Exception

/**
 * 主界面显示的碎片
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewMode = true, useEventBus = true)
class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>() {

    private val downloadUrl = "https://dldir1.qq.com/music/clntupate/QQMusic_YQQFloatLayer.exe"

    override fun getLayoutResId() = R.layout.fragment_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.shareValue = ResUtils.getString(R.string.sample_main_shared_value_between_fragments)
        LogUtils.d(vm)
    }

    private fun addSubscriptions() {
        vm.getObservable(User::class.java).observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> {
                    showShort(R.string.sample_main_got_user, it.data)
                }
                Status.FAILED -> {
                    showShort(it.errorMessage)
                }
                Status.LOADING -> {
                    // do nothing
                }
                else -> {
                    // do nothing
                }
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun initViews() {
        binding.btnToSecond.setOnClickListener {
            val fragment = SecondFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.addToBackStack("Second")
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }
        binding.btnRequestUser.setOnClickListener {
            vm.requestUserData()
        }
        binding.btnToComponentB.setOnClickListener {
            ARouter.getInstance().build("/eyepetizer/main").navigation()
        }
        binding.btnToSample.setOnClickListener {
            ContainerActivity.open(SampleFragment::class.java)
                .put(SampleFragment.ARGS_KEY_TEXT, "Here is the text from the arguments.")
                .launch(context!!)
        }
        binding.btnDownload.setOnClickListener {
            Downloader.getInstance()
                .setOnlyWifi(true)
                .download(downloadUrl, PathUtils.getExternalStoragePath(), object : DownloadListener {
                    override fun onError(errorCode: Int) {
                        showShort("Download : error $errorCode")
                    }

                    override fun onStart() {
                        showShort("Download : start")
                    }

                    override fun onProgress(readLength: Long, contentLength: Long) {
                        LogUtils.d("Download : onProgress $readLength/$contentLength")
                    }

                    override fun onComplete(file: File?) {
                        showShort("Download : complete : ${file?.absoluteFile}")
                    }
                })
        }
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        showShort(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg)
    }

}