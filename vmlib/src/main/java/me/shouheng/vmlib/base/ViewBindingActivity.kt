package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import me.shouheng.utils.stability.L
import java.lang.reflect.ParameterizedType

/** Base activity support view binding. */
abstract class ViewBindingActivity<U : BaseViewModel, T : ViewBinding> : BaseActivity<U>() {

    private lateinit var _binding: T

    protected val binding: T
        get() = _binding

    /** Don't need to use the layout resource id anymore. */
    @Deprecated(
        message = "Deprecated method, the root view will be derived from view binding.",
        replaceWith = ReplaceWith("-1")
    )
    override fun getLayoutResId(): Int = -1

    override fun setupContentView(savedInstanceState: Bundle?) {
        val vbClass: Class<T> = ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments)
            .firstOrNull { ViewBinding::class.java.isAssignableFrom(it as Class<*>) } as? Class<T>
            ?: throw IllegalStateException("You must specify a view binding class.")
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        try {
            _binding = method.invoke(null, LayoutInflater.from(context)) as T
            setContentView(_binding.root)
        } catch (e: Throwable) {
            L.e("Failed to inflate view binding,", e)
        }
    }

    /** Check if binding is initialized. */
    protected fun isBindingInitialized(): Boolean = ::_binding.isInitialized
}
