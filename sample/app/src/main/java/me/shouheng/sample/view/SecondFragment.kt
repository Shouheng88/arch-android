package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.mvvm.annotation.ShareViewModel
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentSecondBinding
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.stability.LogUtils

/**
 * The second fragment used to test shared view model.
 *
 * @author WngShhng 2019-6-29
 */
@ShareViewModel
class SecondFragment : CommonFragment<FragmentSecondBinding, SharedViewModel>() {

    override fun getLayoutResId() = R.layout.fragment_second

    override fun doCreateView(savedInstanceState: Bundle?) {
        LogUtils.d(vm)
        // Get and display shared value from MainFragment
        binding.tv.text = vm.shareValue
    }
}