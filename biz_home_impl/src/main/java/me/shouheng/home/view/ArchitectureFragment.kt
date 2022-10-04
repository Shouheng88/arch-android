package me.shouheng.home.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.shouheng.home.R
import me.shouheng.home.databinding.HomeFragmentArchitectureBinding
import me.shouheng.home.vm.ArchitectureViewModel
import me.shouheng.home.widget.afterTextChanged
import me.shouheng.utils.ktx.gone
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.component.observeOn

/**
 * Input sample fragment.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@FragmentConfiguration(shareViewModel = true)
class ArchitectureFragment: ViewBindingFragment<ArchitectureViewModel, HomeFragmentArchitectureBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        initViews()
        observe()
    }

    private fun initViews() {
        val isSubChild = arguments?.getBoolean(ARG_KEY_IS_SUB_CHILD) == true

        if (isSubChild) {
            binding.bar.gone()
            binding.childContainer.gone()
            binding.btnAddChild.gone()
        } else {
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
            (activity as AppCompatActivity).supportActionBar?.title = stringOf(R.string.main_library_sample)
        }

        binding.etTitle.afterTextChanged { vm.setTitle(it.toString()) }
        binding.etContent.afterTextChanged { vm.setContent(it.toString()) }

        binding.btnTitleError.onDebouncedClick { vm.causeTitleFailure() }
        binding.btnContentError.onDebouncedClick { vm.causeContentFailure() }
        binding.btnAddChild.onDebouncedClick { replaceFragmentNecessary() }
    }

    private fun replaceFragmentNecessary() {
        val fragment = childFragmentManager.findFragmentById(R.id.child_container)
        if (fragment == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.child_container, ArchitectureFragment().apply {
                    this.arguments = Bundle().apply {
                        this.putBoolean(ARG_KEY_IS_SUB_CHILD, true)
                    }
                })
                .commit()
        }
    }

    private fun observe() {
        /*
         * This is a invalid observe method, since events of title and content change
         * all are followed with a sid [ArchitectureViewModel.ARCHITECTURE_SAMPLE_SID_TITLE]
         * and [ArchitectureViewModel.ARCHITECTURE_SAMPLE_SID_CONTENT].
         */
        observeOn(String::class.java) {
            onSuccess {
                toast(it.data)
            }
        }

        /* This is a [observe] styled observe method on string with sid [ArchitectureViewModel.ARCHITECTURE_SAMPLE_SID_TITLE]. */
        observeOn(String::class.java, ArchitectureViewModel.ARCHITECTURE_SAMPLE_SID_TITLE) {
            withSticky(false) // Force none sticky!!
            onSuccess {
                L.d("title value changed [${it.data}]")
                binding.tvTitle.text = stringOf(R.string.main_input_title_with, it.data)
            }
            onFail {
                binding.tvTitle.text = stringOf(R.string.main_input_title_with, it.message)
            }
        }

        /* This is a [observeOn] styled observe method on string with sid [ArchitectureViewModel.ARCHITECTURE_SAMPLE_SID_CONTENT]. */
        observeOn(String::class.java, ArchitectureViewModel.ARCHITECTURE_SAMPLE_SID_CONTENT) {
            withSticky(true)
            onSuccess {
                L.d("content value changed [${it.data}]")
                binding.tvContent.text = stringOf(R.string.main_input_content_with, it.data)
            }
            onFail {
                binding.tvContent.text = stringOf(R.string.main_input_content_with, it.message)
            }
        }
    }

    private companion object {
        private const val ARG_KEY_IS_SUB_CHILD = "__is_sub_child"
    }
}
