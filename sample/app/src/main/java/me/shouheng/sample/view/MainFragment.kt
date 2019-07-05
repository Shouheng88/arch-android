package me.shouheng.sample.view

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.test.OnGetUserListener
import me.shouheng.api.test.UserService
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.stability.LogUtils
import me.shouheng.utils.ui.ToastUtils
import org.greenrobot.eventbus.Subscribe

/**
 * The main fragment.
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewMode = true, useEventBus = true)
class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>(), OnGetUserListener {

    private lateinit var userService: UserService

    override fun getLayoutResId() = R.layout.fragment_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.shareValue = "The shared from MainFragment"
        LogUtils.d(vm)
    }

    private fun addSubscriptions() {
        userService = ARouter.getInstance().navigation(UserService::class.java)
        userService.registerGetUserListener(this)
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
            userService.requestUser()
        }
        binding.btnToComponentB.setOnClickListener {
            ARouter.getInstance().build("/componentb/main").navigation()
        }
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        ToastUtils.showShort("MainFragment:${simpleEvent.msg}")
    }

    override fun onGetUser() {
        ToastUtils.showShort("Get User!")
    }

    override fun onDestroy() {
        super.onDestroy()
        userService.unregisterGetUserListener(this)
    }
}