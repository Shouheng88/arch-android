package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/** Base fragment for view binding. */
abstract class ViewBindingFragment<U : BaseViewModel, T : ViewBinding> : BaseFragment<U>() {

    private lateinit var _binding: T

    protected val binding: T
        get() = _binding

    /** useless */
    @Deprecated(
        message = "Deprecated method, the root view will be derived from view binding.",
        replaceWith = ReplaceWith("-1")
    )
    override fun getLayoutResId(): Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vbClass: Class<T> = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments)
            .firstOrNull { ViewBinding::class.java.isAssignableFrom(it as Class<*>) } as? Class<T>
            ?: throw IllegalStateException("You must specify a view binding class.")
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        // fix 2021-06-26 there is no need to try exception, since even if you caught the exception,
        // it will throw another exception when trying to get root from binding.
        _binding = method.invoke(null, inflater) as T
        // fix 2020-06-27 remove #doCreateView() callback since it will be called after #onViewCreated()
        return _binding.root
    }

    /** Check if binding is initialized. */
    protected fun isBindingInitialized(): Boolean = ::_binding.isInitialized
}
