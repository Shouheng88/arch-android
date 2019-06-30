package me.shouheng.sample.view

import android.os.Bundle
import androidx.lifecycle.Observer
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.data.Status
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityMainBinding
import me.shouheng.sample.vm.MainViewModel
import me.shouheng.utils.ui.ToastUtils

/**
 * The sample main activity
 *
 * @author Wngshhng 2019-6-29
 */
class MainActivity : CommonActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutResId() = R.layout.activity_main

    override fun doCreateView(savedInstanceState: Bundle?) {
        addSubscriptions()
        initViews()
        vm.startLoad()
    }

    private fun addSubscriptions() {
        vm.getObservable(String::class.java).observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    ToastUtils.showShort(it.data)
                }
                Status.FAILED -> {
                    ToastUtils.showShort(it.message)
                }
                Status.LOADING -> {
                    ToastUtils.showShort(it.message)
                }
                else -> {
                    // do nothing
                }
            }
        })
    }

    private fun initViews() {
        val fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
