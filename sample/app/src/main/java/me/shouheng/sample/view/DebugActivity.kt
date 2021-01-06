package me.shouheng.sample.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityDebugBinding
import me.shouheng.sample.event.SimpleEvent
import me.shouheng.sample.event.StartForResult
import me.shouheng.sample.vm.MainViewModel
import me.shouheng.utils.data.StringUtils
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import org.greenrobot.eventbus.Subscribe

/**
 * MVVM Debug activity
 *
 * @author Wngshhng 2019-6-29
 */
@ActivityConfiguration(useEventBus = true)
class DebugActivity : CommonActivity<MainViewModel, ActivityDebugBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_debug

    override fun doCreateView(savedInstanceState: Bundle?) {
        initViews()
        observers()
        vm.startLoad()
    }

    private fun initViews() {
        val fragment = DebugFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        setSupportActionBar(binding.toolbar)
    }

    private fun observers() {
        observe(String::class.java, { toast(it.data) }, { toast(it.message) })
        onResult(0) { code, data ->
            if (code == Activity.RESULT_OK) {
                val ret = data?.getStringExtra("__result")
                L.d("Got result: $ret")
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
