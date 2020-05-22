package me.shouheng.sample.view

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_second.*
import me.shouheng.sample.R
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.BaseFragment
import me.shouheng.vmlib.bus.Bus

/**
 * 用来测试 Fragment 之间共享 ViewModel 的另一个 Fragment
 * Sample also for kotlin android extension to avoid findViewById.
 *
 * @author WngShhng 2019-6-29
 */
@FragmentConfiguration(shareViewModel = true)
class SecondFragment : BaseFragment<SharedViewModel>() {

    override fun getLayoutResId(): Int = R.layout.fragment_second

    override fun doCreateView(savedInstanceState: Bundle?) {
        L.d(vm)
        // Get and display shared value from MainFragment
        tv.text = vm.shareValue
        btn_post.setOnClickListener {
            Bus.get().post(SimpleEvent("MSG#00001"))
        }
    }
}