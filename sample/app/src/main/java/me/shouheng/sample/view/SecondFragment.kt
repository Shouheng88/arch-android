package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.base.anno.FragmentConfiguration
import me.shouheng.mvvm.bus.Bus
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentSecondBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.stability.L

/**
 * 用来测试 Fragment 之间共享 ViewModel 的另一个 Fragment
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewModel = true)
class SecondFragment : CommonFragment<SharedViewModel, FragmentSecondBinding>() {

    override fun getLayoutResId(): Int = R.layout.fragment_second

    override fun doCreateView(savedInstanceState: Bundle?) {
        L.d(vm)
        // Get and display shared value from MainFragment
        binding.tv.text = vm.shareValue
        binding.btnPost.setOnClickListener {
            Bus.get().post(SimpleEvent("MSG#00001"))
        }
    }
}