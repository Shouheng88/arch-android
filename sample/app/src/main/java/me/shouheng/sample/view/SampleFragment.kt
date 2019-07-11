package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.mvvm.base.CommonFragment
import me.shouheng.mvvm.common.EmptyViewModel
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentSampleBinding

/**
 * Sample fragment for ContainerActivity
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
class SampleFragment : CommonFragment<FragmentSampleBinding, EmptyViewModel>() {

    companion object {
        const val ARGS_KEY_TEXT = "__args_key_text"
    }

    override fun getLayoutResId() = R.layout.fragment_sample

    override fun doCreateView(savedInstanceState: Bundle?) {
        val text = arguments?.getString(ARGS_KEY_TEXT)
        binding.tv.text = text
    }

}