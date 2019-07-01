package me.shouheng.component.api

import com.alibaba.android.arouter.facade.template.IProvider

interface UserService : IProvider {

    fun requestUser()

    fun registerUserChangeListener(userChangeListener: OnUserChangeListener)

    fun unRegisterUserChangeListener(userChangeListener: OnUserChangeListener)

    fun registerGetUserListener(getUserListener: OnGetUserListener)

    fun unregisterGetUserListener(getUserListener: OnGetUserListener)
}