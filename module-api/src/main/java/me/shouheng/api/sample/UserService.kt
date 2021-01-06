package me.shouheng.api.sample

import com.alibaba.android.arouter.facade.template.IProvider
import me.shouheng.api.bean.User

/** Service to get user data */
interface UserService : IProvider {

    /** Request user data */
    fun requestUser()

    /** Register user data change listener */
    fun registerUserChangeListener(userChangeListener: OnUserChangeListener)

    /** Unregister user data change listener */
    fun unRegisterUserChangeListener(userChangeListener: OnUserChangeListener)
}

/** User data change listener */
interface OnUserChangeListener {

    /** Called when user data changed */
    fun onUserChanged(user: User)

}
