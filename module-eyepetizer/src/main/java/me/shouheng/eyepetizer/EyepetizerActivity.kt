package me.shouheng.eyepetizer

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.bean.Item
import me.shouheng.eyepetizer.databinding.ActivityEyepetizerBinding
import me.shouheng.eyepetizer.vm.EyepetizerViewModel
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.tools.StateObserver

/**
 * 开眼视频相关的演示页
 *
 * @author WngShhng 2019-07-06
 */
@Route(path = "/eyepetizer/main")
class EyepetizerActivity : CommonActivity<EyepetizerViewModel, ActivityEyepetizerBinding>() {

    private lateinit var adapter: HomeAdapter
    private var loading : Boolean = false

    override fun getLayoutResId() = R.layout.activity_eyepetizer

    override fun doCreateView(savedInstanceState: Bundle?) {
        initView()
        addSubscriptions()
        vm.requestFirstPage()
    }

    private fun initView() {
        adapter = HomeAdapter(this)
        adapter.setOnItemClickListener { _, _, position ->
            val itemList = adapter.data[position]
            ARouter.getInstance().build("/eyepetizer/details")
                .withSerializable(VideoDetailActivity.EXTRA_ITEM, itemList)
                .navigation()
        }
        binding.rv.adapter = adapter
        val layoutManager = binding.rv.layoutManager as LinearLayoutManager
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItem + 1 == totalItemCount && dy > 0) {
                    if (!loading) {
                        loading = true
                        vm.requestNextPage()
                    }
                }
            }
        })
    }

    private fun addSubscriptions() {
        observe(HomeBean::class.java, StateObserver {
            loading = false
            L.d(it.data)
            val list = mutableListOf<Item>()
            it.data.issueList.forEach { issue ->
                issue.itemList.forEach { item ->
                    if (item.data.cover != null
                        && item.data.author != null
                    ) list.add(item)
                }
            }
            adapter.addData(list)
        }, StateObserver { loading = false }, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.eyepetizer_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {R.id.eye_item_request_user -> { vm.requestUser() } }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        ActivityUtils.finishActivity(this, ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
    }
}
