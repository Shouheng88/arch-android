package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.mvvm.annotation.ShareViewModel
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentMainBinding
import me.shouheng.sample.vm.SharedViewModel
import me.shouheng.utils.stability.LogUtils

/**
 * The main fragment.
 *
 * @author WngShhng 2019-6-29
 */
@ShareViewModel
class MainFragment : CommonFragment<FragmentMainBinding, SharedViewModel>() {

    override fun getLayoutResId() = R.layout.fragment_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.shareValue = "The shared from MainFragment"
        LogUtils.d(vm)
    }

    private fun addSubscriptions() {
        // current do nothing
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
    }
}