package me.shouheng.sample.view

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.sample.databinding.ActivitySplashBinding
import me.shouheng.vmlib.base.ViewBindingActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/** Splash page. */
class SplashActivity : ViewBindingActivity<EmptyViewModel, ActivitySplashBinding>() {

    override fun doCreateView(savedInstanceState: Bundle?) {
        ARouter.getInstance().build("/home/entrance").navigation()
        finish()
    }
}
