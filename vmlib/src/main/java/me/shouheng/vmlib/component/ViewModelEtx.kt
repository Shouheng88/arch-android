package me.shouheng.vmlib.component

import androidx.lifecycle.ViewModelProviders
import me.shouheng.vmlib.base.BaseActivity
import me.shouheng.vmlib.base.BaseFragment
import me.shouheng.vmlib.base.BaseViewModel

/** Get new viewmodel for fragment from its generic type. */
inline fun <reified VM : BaseViewModel> BaseFragment<VM>.newViewModel(shareViewModel: Boolean = false): VM {
    return if (shareViewModel) {
        ViewModelProviders.of(activity!!)[VM::class.java]
    } else {
        ViewModelProviders.of(this)[VM::class.java]
    }
}

/** Get new viewmodel for activity from its generic type. */
inline fun <reified VM : BaseViewModel> BaseActivity<VM>.newViewModel(): VM {
    return ViewModelProviders.of(this)[VM::class.java]
}
