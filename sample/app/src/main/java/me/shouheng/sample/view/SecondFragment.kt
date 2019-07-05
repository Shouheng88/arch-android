package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.bus.EventBusManager
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentSecondBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.stability.LogUtils

/**
 * 用来测试 Fragment 之间共享 ViewModel 的另一个 Fragment
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewMode = true)
class SecondFragment : CommonFragment<FragmentSecondBinding, SharedViewModel>() {

    override fun getLayoutResId() = R.layout.fragment_second

    override fun doCreateView(savedInstanceState: Bundle?) {
        LogUtils.d(vm)
        // Get and display shared value from MainFragment
        binding.tv.text = vm.shareValue
        binding.btnPost.setOnClickListener {
            EventBusManager.getInstance().post(SimpleEvent("MSG#00001"))
        }
    }
}