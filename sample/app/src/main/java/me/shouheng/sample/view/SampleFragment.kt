package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentSampleBinding
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.CommonFragment
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * Sample fragment for ContainerActivity
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
@FragmentConfiguration
class SampleFragment : CommonFragment<EmptyViewModel, FragmentSampleBinding>() {

    companion object {
        const val ARGS_KEY_TEXT = "__args_key_text"
    }

    override fun getLayoutResId(): Int = R.layout.fragment_sample

    override fun doCreateView(savedInstanceState: Bundle?) {
        val text = arguments?.getString(ARGS_KEY_TEXT)
        binding.tv.text = text
    }

}