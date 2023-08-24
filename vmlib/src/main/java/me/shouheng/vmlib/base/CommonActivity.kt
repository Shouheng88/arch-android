package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * The basic common implementation for MMVMs activity.
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-6-29
 */
abstract class CommonActivity<U : BaseViewModel, T : ViewDataBinding> : BaseActivity<U>() {

    protected var binding: T? = null

    override fun setupContentView(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(this), getLayoutResId(), null, false
        )
        setContentView(binding?.root)
    }
}
