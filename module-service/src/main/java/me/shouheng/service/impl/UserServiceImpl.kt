package me.shouheng.service.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.api.bean.User
import me.shouheng.api.sample.OnUserChangeListener
import me.shouheng.api.sample.UserService
import me.shouheng.utils.stability.L
import java.util.concurrent.CopyOnWriteArrayList

/**
 * User info service implementation
 *
 * @author WngShhng 2019-07-05
 */
@Route(path = "/api/user")
class UserServiceImpl : UserService {

    private val listeners = CopyOnWriteArrayList<OnUserChangeListener>()

    override fun requestUser() {
        for (listener in listeners) {
            listener.onUserChanged(User("Jeff", 18))
        }
    }

    override fun registerUserChangeListener(userChangeListener: OnUserChangeListener) {
        if (!listeners.contains(userChangeListener)) {
            listeners.add(userChangeListener)
        }
    }

    override fun unRegisterUserChangeListener(userChangeListener: OnUserChangeListener) {
        listeners.remove(userChangeListener)
    }

    override fun init(context: Context?) {
        L.i(UserServiceImpl::class.java.name + " initialized.")
    }

}