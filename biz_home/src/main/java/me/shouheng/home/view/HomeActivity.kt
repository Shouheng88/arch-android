package me.shouheng.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.home.R
import me.shouheng.home.databinding.HomeActivityBinding
import me.shouheng.home.event.SimpleEvent
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.dp
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.ui.BarUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.comn.EmptyViewModel
import org.greenrobot.eventbus.Subscribe

/**
 * VMLib main activity. Data binding styled page.
 *
 * - 1. Extend [CommonActivity] to use databinding feature;
 * - 2. Use [EmptyViewModel], if the page don't need the viewmodel;
 * - 3. Use [ActivityConfiguration.useEventBus] to let the activity receive event from eventbus;
 * - 4. Use [ActivityConfiguration.exitDirection] to specify the animation direction for this activity;
 * - 5. Use [Subscribe] annotation to let the method receive event from event bus.
 *
 * @author ShouhengWang 2019-6-29
 */
@Route(path = "/home/entrance")
@ActivityConfiguration(
    useEventBus = true,
    exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP
)
class HomeActivity : CommonActivity<EmptyViewModel, HomeActivityBinding>() {

    private var onBackPressed: Long = -1

    override fun getLayoutResId(): Int = R.layout.home_activity

    override fun doCreateView(savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        BarUtils.setStatusBarLightMode(this, true)
        setSupportActionBar(binding!!.toolbar)
        supportActionBar?.setTitle(R.string.app_name)
        binding!!.lsv.update(0f.dp())
        binding!!.llEye.onDebouncedClick {
            ARouter.getInstance().build("/eyepetizer/main").navigation()
            ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_FORWARD)
        }
        binding!!.llEt.onDebouncedClick {
            /* Sample for [ContainerActivity]. */
            ContainerActivity.open(ArchitectureFragment::class.java)
                .put(ContainerActivity.KEY_EXTRA_ACTIVITY_DIRECTION, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
                .put(ContainerActivity.KEY_EXTRA_THEME_ID, R.style.RedAppTheme)
                .launch(context)
            ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
        }
        binding!!.llMore.onDebouncedClick {
            /* [ContainerActivity] use default animation. */
            ContainerActivity.open(MoreFragment::class.java)
                .put(ContainerActivity.KEY_EXTRA_THEME_ID, R.style.GreenAppTheme)
                .launch(context)
        }
        binding!!.llGuokr.onDebouncedClick {
            ContainerActivity.openWith(GuokrFragmentProvider::class.java).launch(context)
        }
    }

    @Subscribe
    fun onGetMessage(event: SimpleEvent) {
        toast(stringOf(R.string.main_more_widget_make_a_post_receiver)
            .format(event.msg, this.javaClass.simpleName))
    }

    override fun onBackPressed() {
        if (onBackPressed + 2000L > System.currentTimeMillis()) {
            superOnBackPressed()
        } else {
            toast(R.string.main_again_to_exit)
        }
        onBackPressed = System.currentTimeMillis()
    }

    companion object {
        const val HOME_COMMAND_LAUNCH_GUOKR = 0x00011
    }
}
