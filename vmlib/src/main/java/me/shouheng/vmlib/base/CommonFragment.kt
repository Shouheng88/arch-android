package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * The base common fragment implementation for MVVMs. Example:
 *
 * @author [ShouhengWang](mailto:shouheng2020@gmail.com)
 * @version 2019-6-29
 */
abstract class CommonFragment<U : BaseViewModel, T : ViewDataBinding> : BaseFragment<U>() {

    private lateinit var _binding: T

    protected val binding: T
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutResId = getLayoutResId()
        require(layoutResId > 0) { "The subclass must provider a valid layout resources id." }
        _binding = DataBindingUtil.inflate(inflater, layoutResId, null, false)
        // fix 2020-06-27 remove #doCreateView() callback since it will be called after #onViewCreated()
        return _binding.root
    }

    /** Check if binding is initialized. */
    protected fun isBindingInitialized(): Boolean = ::_binding.isInitialized
}
