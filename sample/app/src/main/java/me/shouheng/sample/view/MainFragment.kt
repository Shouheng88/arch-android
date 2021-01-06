package me.shouheng.sample.view

import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.event.StartForResult
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.data.StringUtils
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.CommonFragment
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.network.download.DownloadListener
import me.shouheng.vmlib.network.download.Downloader
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
        vm.shareValue = stringOf(R.string.sample_main_shared_value_between_fragments)
        L.d(vm)
    }

    private fun addSubscriptions() {
        // 监听用户消息
        observe(User::class.java, {
            toast(StringUtils.format(R.string.sample_main_got_user, it.data))
        }, { toast(it.message) })

        // ============================ 测试 VM 的 getObservable 系列方法 ============================
        // 监听：String+Flag#0x0001，指定了 single=true，两个注册只有一个能收到
        observe(String::class.java, SID_1, true, {
            toast("#1.1: " + it.data)
            L.d("#1.1: " + it.data)
        })
        observe(String::class.java, SID_1, true, {
            toast("#1.2: " + it.data)
            L.d("#1.2: " + it.data)
        })

        // 监听：String+Flag#0x0002，默认 single=true，两个注册都能收到消息
        observe(String::class.java, SID_2, {
            toast("#2.1: ${it.data}")
            L.d("#2.1: " + it.data)
        })
        observe(String::class.java, SID_2, {
            toast("#2.2: ${it.data}")
            L.d("#2.2: " + it.data)
        })

        // 监听：List<String>+Flag#0x0001
        observeList(String::class.java, SID_1, { toast("L#1: ${it.data}") })

        // 监听：List<String>+Flag#0x0002
        observeList(String::class.java, SID_2, { toast("L#2: ${it.data}") })
    }

    @SuppressLint("MissingPermission")
    private fun initViews() {
        binding.btnToSecond.onDebouncedClick {
            val fragment = SecondFragment()
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.addToBackStack("Second")
                ?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }
        binding.btnObservable.onDebouncedClick {
            // 测试发送 String+Flag 类型的消息，正确收发则测试通过
            if (it.tag == null) {
                it.tag = "SSS"
                vm.testObservableFlag(SID_1)
            } else {
                it.tag = null
                vm.testObservableFlag(SID_2)
            }
        }
        binding.btnObservableList.onDebouncedClick {
            // 测试发送 List+Flag 类型的消息，正确收发则测试通过
            if (it.tag == null) {
                it.tag = "SSS"
                vm.testObservableListFlag(SID_1)
            } else {
                it.tag = null
                vm.testObservableListFlag(SID_2)
            }
        }
        binding.btnRequestUser.onDebouncedClick { vm.requestUserData() }
        binding.btnToComponentB.onDebouncedClick {
            ARouter.getInstance().build("/eyepetizer/main").navigation()
            ActivityUtils.overridePendingTransition(activity!!, ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
        }
        binding.btnToSample.onDebouncedClick {
            ContainerActivity.open(SampleFragment::class.java)
                .put(SampleFragment.ARGS_KEY_TEXT, stringOf(R.string.sample_main_argument_to_fragment))
                .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_BACK)
                .withDirection(ActivityDirection.ANIMATE_FORWARD)
                .launch(context!!)
        }
        binding.btnDownload.onDebouncedClick {
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
        binding.btnUtils.onDebouncedClick {
            ActivityUtils.start(context!!, MainActivity::class.java)
        }
        binding.btnPref.onDebouncedClick {
            ContainerActivity.openFragment(SamplePreference::class.java)
                // Specify the container activity theme.
                .put(ContainerActivity.KEY_EXTRA_THEME_ID, R.style.TestAppTheme)
                // Specify the activity enter and exit direction.
                .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
                .withDirection(ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
                .launch(context!!)
        }
        binding.btnCrash.onDebouncedClick { 1/0 }
        binding.btnVmPost.onDebouncedClick { vm.postMessage() }
        binding.btnStartResult.onDebouncedClick { post(StartForResult(0)) }
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }

    companion object {
        const val SID_1:Int = 0x0001
        const val SID_2:Int = 0x0002
    }
}