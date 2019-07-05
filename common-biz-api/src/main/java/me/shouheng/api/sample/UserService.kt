package me.shouheng.api.sample

import com.alibaba.android.arouter.facade.template.IProvider
import me.shouheng.api.bean.User

/**
 * Sample 模块用来模拟获取用户信息的服务接口
 *
 * @author WngShhng 2019-07-05
 */
interface UserService : IProvider {

    /**
     * 请求用户信息，请求的结果将会通过下面的注册家庭的接口获取到
     */
    fun requestUser()

    /**
     * 注册用户信息发生变化的监听
     *
     * @param userChangeListener 用户信息变化的回调接口
     */
    fun registerUserChangeListener(userChangeListener: OnUserChangeListener)

    fun unRegisterUserChangeListener(userChangeListener: OnUserChangeListener)
}

/**
 * 当用户信息发生变化时的监听接口
 */
interface OnUserChangeListener {

    /**
     * 用户信息发生变化时回调的方法
     */
    fun onUserChanged(user: User)
}
