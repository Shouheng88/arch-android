package me.shouheng.eyepetizer

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.bean.Item
import me.shouheng.eyepetizer.databinding.ActivityEyepetizerBinding
import me.shouheng.mvvm.base.CommonActivity
import me.shouheng.mvvm.data.Status
import me.shouheng.utils.stability.LogUtils


@Route(path = "/eyepetizer/main")
class EyepetizerActivity : CommonActivity<ActivityEyepetizerBinding, EyepetizerViewModel>() {

    private lateinit var adapter: HomeAdapter

    override fun getLayoutResId() = R.layout.activity_eyepetizer

    override fun doCreateView(savedInstanceState: Bundle?) {
        initView()
        addSubscriptions()
        vm.reuestFirstPage()
    }

    private fun initView() {
        adapter = HomeAdapter(this)
        adapter.setOnItemClickListener { _, _, position ->
            val itemList = adapter.data[position]
            showShort(itemList.data.playUrl)
        }
        binding.rv.adapter = adapter
    }

    private fun addSubscriptions() {
        vm.getObservable(HomeBean::class.java).observe(this, Observer { resources
            ->
            when (resources!!.status) {
                Status.SUCCESS -> {
                    LogUtils.d(resources.data)
                    val list = mutableListOf<Item>()
                    resources.data.issueList.forEach {
                        it.itemList.forEach { item ->
                            if ("banner2" != item.type) list.add(item)
                        }
                    }
                    adapter.addData(list)
                }
                Status.FAILED -> {
                    showShort(resources.message)
                }
                Status.LOADING -> {
                    showShort(resources.message)
                }
                else -> {
                    // do nothing
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.eyepetizer_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.eye_item_request_user -> {
                vm.requestUser()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
