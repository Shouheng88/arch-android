package me.shouheng.sample.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.User
import me.shouheng.api.sample.OnUserChangeListener
import me.shouheng.api.sample.UserService
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.vmlib.base.BaseViewModel
import me.shouheng.vmlib.bean.Resources

/**
 * 在 Fragment 之间共享的 ViewModel
 *
 * @author Wngshhng 2019-6-29
 */
class SharedViewModel(application: Application) : BaseViewModel(application), OnUserChangeListener {

    val resLiveData = MutableLiveData<Resources<String>>()

    /**
     * ViewModel 直接与 Service 进行交互而不是 View，
     * ViewModel 与 View 之间通过 LiveData 进行交互
     */
    private var userService: UserService = ARouter.getInstance().navigation(UserService::class.java)

    /**
     * Fragment 之间共享的值
     */
    var shareValue: String? = null

    init {
        userService.registerUserChangeListener(this)
    }

    fun requestUserData() {
        userService.requestUser()
    }

    fun postMessage() {
        post(SimpleEvent("Message from SharedViewModel!"))
    }

    fun testObservableFlag(sid: Int) {
        setSuccess(String::class.java, "测试 Observable + Sid #$sid !", sid)
    }

    fun testObservableListFlag(sid: Int) {
        setListSuccess(String::class.java, listOf("测试 List Observable + Sid #$sid !"), sid)
    }

    override fun onUserChanged(user: User) { setSuccess(User::class.java, user) }

    override fun onCleared() {
        super.onCleared()
        userService.unRegisterUserChangeListener(this)
    }
}