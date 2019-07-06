package me.shouheng.api.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.api.bean.User
import me.shouheng.api.sample.OnUserChangeListener
import me.shouheng.api.sample.UserService
import me.shouheng.utils.stability.LogUtils
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 用户信息服务实现
 *
 * @author WngShhng 2019-07-05
 */
@Route(path = "/api/user")
class UserServiceImpl : UserService {

    private val userChangeListeners = CopyOnWriteArrayList<OnUserChangeListener>()

    override fun requestUser() {
        for (listener in userChangeListeners) {
            listener.onUserChanged(User("WngShhng", 18))
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

    override fun init(context: Context?) {
        LogUtils.i(UserServiceImpl::class.java.name + " has init.")
    }

}