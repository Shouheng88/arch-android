package me.shouheng.sample.view

import android.os.Bundle
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.vm.MainViewModel
import me.shouheng.utils.data.StringUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.tools.StateObserver
import org.greenrobot.eventbus.Subscribe

/**
 * MVVM 框架演示工程
 *
 * @author Wngshhng 2019-6-29
 */
@ActivityConfiguration(useEventBus = false)
class MainActivity : CommonActivity<MainViewModel, ActivityMainBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.startLoad()
    }

    private fun addSubscriptions() {
        observe(String::class.java, StateObserver {
            toast(it.data)
        }, StateObserver { toast(it.message) }, null)
    }

    private fun initViews() {
        val fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        setSupportActionBar(binding.toolbar)
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }
}
