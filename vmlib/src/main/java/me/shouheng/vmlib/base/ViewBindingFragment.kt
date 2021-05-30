package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.ViewBinding
import me.shouheng.utils.stability.L
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType

abstract class ViewBindingFragment<U : BaseViewModel, T : ViewBinding> : BaseFragment<U>() {

    protected lateinit var binding: T
        private set

    /* useless */
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
        try {
            binding = method.invoke(null, LayoutInflater.from(context)) as T
        } catch (e: Exception) {
            L.e("Failed to inflate view binding: ", e)
        }
        // fix 2020-06-27 remove #doCreateView() callback since it will be called after #onViewCreated()
        return binding.root
    }

}