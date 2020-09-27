package me.shouheng.sample.view

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.data.StringUtils
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.CommonFragment
import me.shouheng.vmlib.bean.Status
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.network.download.DownloadListener
import me.shouheng.vmlib.network.download.Downloader
import me.shouheng.vmlib.tools.StateObserver
import org.greenrobot.eventbus.Subscribe
import java.io.File

/**
 * 主界面显示的碎片
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewModel = true, useEventBus = true)
class MainFragment : CommonFragment<SharedViewModel, FragmentMainBinding>() {

    private val downloadUrl = "https://images.unsplash.com/photo-1501879779179-4576bae71d8" +
            "d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&dl=vladimir-riabinin-diMBLU4FzDQ-unsplash.jpg"

    override fun getLayoutResId(): Int = R.layout.fragment_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.shareValue = ResUtils.getString(R.string.sample_main_shared_value_between_fragments)
        L.d(vm)
    }

    private fun addSubscriptions() {
        // 监听用户消息
        observe(User::class.java, StateObserver {
            toast(StringUtils.format(R.string.sample_main_got_user, it.data))
        }, StateObserver { toast(it.message) }, null)

        // ============================ 测试 VM 的 getObservable 系列方法 ============================
        // 监听：String+Flag#0x0001，指定了 single=true，两个注册只有一个能收到
        observe(String::class.java, FLAG_1, true, StateObserver {
            toast("#1.1: " + it!!.data)
            L.d("#1.1: " + it.data)
        }, null, null)
        observe(String::class.java, FLAG_1, true, StateObserver {
            toast("#1.2: " + it!!.data)
            L.d("#1.2: " + it.data)
        }, null, null)

        // 监听：String+Flag#0x0002，默认 single=true，两个注册都能收到消息
        observe(String::class.java, FLAG_2, StateObserver {
            toast("#2.1: ${it!!.data}")
            L.d("#2.1: " + it.data)
        }, null, null)
        observe(String::class.java, FLAG_2, StateObserver {
            toast("#2.2: ${it!!.data}")
            L.d("#2.2: " + it.data)
        }, null, null)

        // 监听：List<String>+Flag#0x0001
        observeList(String::class.java, FLAG_1, StateObserver {
            toast("L#1: ${it!!.data}")
        }, null, null)

        // 监听：List<String>+Flag#0x0002
        observeList(String::class.java, FLAG_2, StateObserver {
            toast("L#2: ${it!!.data}")
        }, null, null)
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
        binding.btnObservable.setOnClickListener {
            // 测试发送 String+Flag 类型的消息，正确收发则测试通过
            if (it.tag == null) {
                it.tag = "SSS"
                vm.testObservableFlag(FLAG_1)
            } else {
                it.tag = null
                vm.testObservableFlag(FLAG_2)
            }
        }
        binding.btnObservableList.setOnClickListener {
            // 测试发送 List+Flag 类型的消息，正确收发则测试通过
            if (it.tag == null) {
                it.tag = "SSS"
                vm.testObservableListFlag(FLAG_1)
            } else {
                it.tag = null
                vm.testObservableListFlag(FLAG_2)
            }
        }
        binding.btnRequestUser.setOnClickListener { vm.requestUserData() }
        binding.btnToComponentB.setOnClickListener {
            ARouter.getInstance().build("/eyepetizer/main").navigation()
            ActivityUtils.overridePendingTransition(activity!!, ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
        }
        binding.btnToSample.setOnClickListener {
            ContainerActivity.open(SampleFragment::class.java)
                .put(SampleFragment.ARGS_KEY_TEXT, ResUtils.getString(R.string.sample_main_argument_to_fragment))
                .launch(context!!)
        }
        binding.btnDownload.setOnClickListener {
            Downloader.getInstance()
                .setOnlyWifi(true)
                .download(downloadUrl, PathUtils.getExternalStoragePath(), object :
                    DownloadListener {
                    override fun onError(errorCode: Int) {
                        toast("Download : error $errorCode")
                    }

                    override fun onStart() {
                        toast("Download : start")
                    }

                    override fun onProgress(readLength: Long, contentLength: Long) {
                        L.d("Download : onProgress $readLength/$contentLength")
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
            ContainerActivity.openFragment(SamplePreference::class.java).launch(context!!)
        }
        binding.btnCrash.setOnClickListener { 1/0 }
        binding.btnVmPost.setOnClickListener { vm.postMessage() }
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }

    companion object {
        const val FLAG_1:Int = 0x0001
        const val FLAG_2:Int = 0x0002
    }
}