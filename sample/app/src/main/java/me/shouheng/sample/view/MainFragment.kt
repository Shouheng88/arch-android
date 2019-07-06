package me.shouheng.sample.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.data.Status
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.stability.LogUtils
import org.greenrobot.eventbus.Subscribe

/**
 * 主界面显示的碎片
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewMode = true, useEventBus = true)
class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>() {

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
                    showShort(it.message)
                }
                Status.LOADING -> {
                    showShort(it.message)
                }
                else -> {
                    // do nothing
                }
            }
        })
    }

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
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        showShort(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg)
    }

}