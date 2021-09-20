package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import me.shouheng.utils.stability.L
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType

/** Base activity support view binding. */
abstract class ViewBindingActivity<U : BaseViewModel, T : ViewBinding> : AbsForwardActivity<U>() {

    protected lateinit var binding: T
        private set

    /** Don't need to use the layout resource id anymore. */
    override fun getLayoutResId(): Int = -1

    override fun setupContentView(savedInstanceState: Bundle?) {
        val vbClass: Class<T> = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments)
            .firstOrNull { ViewBinding::class.java.isAssignableFrom(it as Class<*>) } as? Class<T>
            ?: throw IllegalStateException("You must specify a view binding class.")
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        try {
            binding = method.invoke(null, LayoutInflater.from(context)) as T
            setContentView(binding.root)
        } catch (e: Exception) {
            L.e("Failed to inflate view binding,", e)
        }
    }

}
