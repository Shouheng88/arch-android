package me.shouheng.eyepetizer

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.bean.Item
import me.shouheng.eyepetizer.databinding.ActivityEyepetizerBinding
import me.shouheng.eyepetizer.vm.EyepetizerViewModel
import me.shouheng.uix.widget.rv.Adapter
import me.shouheng.uix.widget.rv.getAdapter
import me.shouheng.uix.widget.rv.listener.DataLoadScrollListener
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity

@Route(path = "/eyepetizer/main")
@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class EyepetizerActivity : CommonActivity<EyepetizerViewModel, ActivityEyepetizerBinding>() {

    private lateinit var adapter: Adapter<Item>

    override fun getLayoutResId() = R.layout.activity_eyepetizer

    override fun doCreateView(savedInstanceState: Bundle?) {
        configList()
        observes()
        vm.firstPage()
    }

    private fun configList() {
        adapter = getAdapter(R.layout.eyepetizer_tem_home, { helper, item ->
            helper.setText(R.id.tv_title, item.data.title)
            helper.setText(R.id.tv_sub_title, item.data.author?.name + " | " + item.data.category)
            Glide.with(context)
                .load(item.data.cover?.homepage)
                .thumbnail(Glide.with(context).load(R.drawable.recommend_summary_card_bg_unlike))
                .into(helper.getView<View>(R.id.iv_cover) as ImageView)
            Glide.with(context)
                .load(item.data.author?.icon)
                .thumbnail(Glide.with(context).load(R.mipmap.eyepetizer))
                .into(helper.getView<View>(R.id.iv_author) as ImageView)
        }, mutableListOf())
        adapter.setOnItemClickListener { _, _, position ->
            val itemList = adapter.data[position]
            ARouter.getInstance().build("/eyepetizer/details")
                .withSerializable(VideoDetailActivity.EXTRA_ITEM, itemList)
                .navigation()
            ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_SCALE_IN)
        }
        binding.rv.setEmptyView(binding.ev)
        binding.rv.adapter = adapter
        binding.rv.addOnScrollListener(object : DataLoadScrollListener(binding.rv.layoutManager as LinearLayoutManager) {
            override fun loadMore() {
                vm.nextPage()
            }
        })
    }

    private fun observes() {
        observe(HomeBean::class.java, {
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
            binding.ev.showEmpty()
        }, {
            toast(it.message)
            binding.ev.showEmpty()
        }, {
            // The udf3 is the flag for loading more.
            // We only need to show loading progress bar for first page.
            if (!it.udf3) {
                binding.ev.showLoading()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.eyepetizer_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {R.id.eye_item_request_user -> { vm.requestUser() } }
        return super.onOptionsItemSelected(item)
    }
}
