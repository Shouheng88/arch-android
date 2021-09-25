package me.shouheng.sample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.shouheng.sample.R
import me.shouheng.sample.databinding.FragmentInputBinding
import me.shouheng.sample.vm.InputViewModel
import me.shouheng.sample.widget.onTextChanged
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ktx.toast
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.component.observeOn

/**
 * Input sample fragment.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
class InputFragment : ViewBindingFragment<InputViewModel, FragmentInputBinding>() {
    override fun doCreateView(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = stringOf(R.string.main_input_sample)

        binding.etTitle.onTextChanged { vm.setTitle(it.toString()) }
        binding.etContent.onTextChanged { vm.setContent(it.toString()) }

        /** This is a invalid observe method, since events of title and content change
         * all are followed with a sid [InputViewModel.SID_TITLE] and [InputViewModel.SID_CONTENT]. */
        observe(String::class.java, {
            toast(it.data)
        })

        /** This is a [observe] styled observe method on string with sid [InputViewModel.SID_TITLE]. */
        observe(String::class.java, InputViewModel.SID_TITLE, {
            binding.tvTitle.text = it.data
        })

        /** This is a [observeOn] styled observe method on string with sid [InputViewModel.SID_CONTENT]. */
        observeOn(String::class.java, InputViewModel.SID_CONTENT) {
            onSuccess {
                binding.tvContent.text = it.data
            }
        }
    }
}
