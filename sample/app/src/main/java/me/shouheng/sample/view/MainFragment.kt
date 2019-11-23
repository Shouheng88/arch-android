package me.shouheng.sample.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.bean.Status
import me.shouheng.mvvm.comn.ContainerActivity
import me.shouheng.mvvm.http.DownloadListener
import me.shouheng.mvvm.http.Downloader
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.data.StringUtils
import me.shouheng.utils.stability.LogUtils
import me.shouheng.utils.store.PathUtils
import org.greenrobot.eventbus.Subscribe
import java.io.File

/**
 * 主界面显示的碎片
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewMode = true, useEventBus = true, layoutResId = R.layout.fragment_main)
class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>() {

    private val downloadUrl = "https://dldir1.qq.com/music/clntupate/QQMusic_YQQFloatLayer.exe"

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.shareValue = ResUtils.getString(R.string.sample_main_shared_value_between_fragments)
        LogUtils.d(vm)
    }

    private fun addSubscriptions() {
        vm.getObservable(User::class.java).observe(this, Observer {
            when (it!!.status) {
                Status.SUCCESS -> { toast(StringUtils.format(R.string.sample_main_got_user, it.data)) }
                Status.FAILED -> { toast(it.message) }
                Status.LOADING -> {/* do nothing */ }
                else -> {/* do nothing */ }
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
            ActivityUtils.overridePendingTransition(activity!!, ActivityUtils.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
        }
        binding.btnToSample.setOnClickListener {
            ContainerActivity.open(SampleFragment::class.java)
                .put(SampleFragment.ARGS_KEY_TEXT, ResUtils.getString(R.string.sample_main_argument_to_fragment))
                .launch(context!!)
        }
        binding.btnDownload.setOnClickListener {
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
                        LogUtils.d("Download : onProgress $readLength/$contentLength")
                    }

                    override fun onComplete(file: File?) {
                        toast("Download : complete : ${file?.absoluteFile}")
                    }
                })
        }
        binding.btnUtils.setOnClickListener {
            ActivityUtils.start(context!!, MainActivity::class.java)
        }
        binding.btnPref.setOnClickListener {
            val sp = SamplePreference()
            activity!!.fragmentManager.beginTransaction().replace(R.id.fragment_container, sp).addToBackStack("").commit()
        }
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }

}