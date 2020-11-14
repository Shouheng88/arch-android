package me.shouheng.sample.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityMainBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.event.StartForResult
import me.shouheng.sample.vm.MainViewModel
import me.shouheng.utils.data.StringUtils
import me.shouheng.utils.ktx.logd
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import org.greenrobot.eventbus.Subscribe

/**
 * MVVM 框架演示工程
 *
 * @author Wngshhng 2019-6-29
 */
@ActivityConfiguration(useEventBus = true)
class MainActivity : CommonActivity<MainViewModel, ActivityMainBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        initViews()
        observers()
        vm.startLoad()
    }

    private fun initViews() {
        val fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        setSupportActionBar(binding.toolbar)
    }

    private fun observers() {
        observe(String::class.java, { toast(it.data) }, { toast(it.message) })
        onResult(0) { code, data ->
            if (code == Activity.RESULT_OK) {
                val ret = data?.getStringExtra("__result")
                logd("Got result: $ret")
            }
        }
    }

    @Subscribe
    fun onGetMessage(simpleEvent: SimpleEvent) {
        toast(StringUtils.format(R.string.sample_main_activity_received_msg, javaClass.simpleName, simpleEvent.msg))
    }

    @Subscribe
    fun onGetMessage(startForResult: StartForResult) {
        val intent = Intent(this, SecondActivity::class.java)
        start(intent, 0) { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                val ret = data?.getStringExtra("__result")
                toast("Got result: $ret")
            }
        }
    }
}
