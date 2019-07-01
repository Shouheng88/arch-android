package me.shouheng.component

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.component.api.OnGetUserListener
import me.shouheng.component.api.OnUserChangeListener
import me.shouheng.component.api.UserService
import java.util.concurrent.CopyOnWriteArrayList

@Route(path = "/component/user_service")
class UserServiceImpl : UserService {

    private val userChangeListeners = CopyOnWriteArrayList<OnUserChangeListener>()

    private val getUserListeners = CopyOnWriteArrayList<OnGetUserListener>()

    override fun requestUser() {
        for (listener in userChangeListeners) {
            listener.onUserChanged()
        }
        for (listener in getUserListeners) {
            listener.onGetUser()
        }
    }

    override fun registerUserChangeListener(userChangeListener: OnUserChangeListener) {
        if (!userChangeListeners.contains(userChangeListener)) {
            userChangeListeners.add(userChangeListener)
        }
    }

    override fun unRegisterUserChangeListener(userChangeListener: OnUserChangeListener) {
        if (userChangeListeners.contains(userChangeListener)) {
            userChangeListeners.remove(userChangeListener)
        }
    }

    override fun registerGetUserListener(getUserListener: OnGetUserListener) {
        if (!getUserListeners.contains(getUserListener)) {
            getUserListeners.add(getUserListener)
        }
    }

    override fun unregisterGetUserListener(getUserListener: OnGetUserListener) {
        if (getUserListeners.contains(getUserListener)) {
            getUserListeners.remove(getUserListener)
        }
    }

    override fun init(context: Context?) {
        Log.e("Test ", UserServiceImpl::class.java.name + " has init.")
    }

}