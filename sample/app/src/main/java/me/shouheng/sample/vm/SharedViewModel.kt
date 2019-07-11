package me.shouheng.sample.vm

import android.app.Application
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.api.sample.OnUserChangeListener
import me.shouheng.api.sample.UserService
import me.shouheng.mvvm.base.BaseViewModel
import me.shouheng.mvvm.bean.Resources

/**
 * 在 Fragment 之间共享的 ViewModel
 *
 * @author Wngshhng 2019-6-29
 */
class SharedViewModel(application: Application) : BaseViewModel(application), OnUserChangeListener {

    /**
     * ViewModel 直接与 Service 进行交互而不是 View，
     * ViewModel 与 View 之间通过 LiveData 进行交互
     */
    private lateinit var userService: UserService

    /**
     * Fragment 之间共享的值
     */
    var shareValue: String? = null

    override fun onCreate(extras: Bundle?, savedInstanceState: Bundle?) {
        userService = ARouter.getInstance().navigation(UserService::class.java)
        userService.registerUserChangeListener(this)
    }

    fun requestUserData() {
        userService.requestUser()
    }

    override fun onUserChanged(user: User) {
        getObservable(User::class.java).value = Resources.success(user)
    }

    override fun onDestroy() {
        userService.unRegisterUserChangeListener(this)
    }
}