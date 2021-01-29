package me.shouheng.vmlib.base

import android.os.Bundle
import android.view.LayoutInflater
import android.viewbinding.ViewBinding
import me.shouheng.utils.permission.PermissionResultResolver
import me.shouheng.utils.stability.L
import java.lang.reflect.ParameterizedType

abstract class ViewBindingActivity<U : BaseViewModel, T : ViewBinding> :
    BaseActivity<U>(), PermissionResultResolver {

    protected lateinit var binding: T
        private set

    /** Don't need to use the layout resource id anymore. */
    override fun getLayoutResId(): Int = -1

    override fun setupContentView(savedInstanceState: Bundle?) {
        val vbClass: Class<T> = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        try {
            binding = method.invoke(null, LayoutInflater.from(context)) as T
            setContentView(binding.root)
        } catch (e: Exception) {
            L.e("Failed to ")
        }
    }

}
