package me.shouheng.eyepetizer

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.bean.Item
import me.shouheng.eyepetizer.config.message
import me.shouheng.eyepetizer.databinding.ActivityEyepetizerBinding
import me.shouheng.eyepetizer.vm.EyepetizerViewModel
import me.shouheng.uix.common.glide.CornersTransformation
import me.shouheng.uix.widget.rv.Adapter
import me.shouheng.uix.widget.rv.getAdapter
import me.shouheng.uix.widget.rv.listener.CustomRecyclerScrollViewListener
import me.shouheng.uix.widget.rv.listener.DataLoadScrollListener
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.dp
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.stability.L
import me.shouheng.utils.ui.BarUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.ViewBindingActivity

@Route(path = "/eyepetizer/main")
@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class EyepetizerActivity : ViewBindingActivity<EyepetizerViewModel, ActivityEyepetizerBinding>() {

    private lateinit var adapter: Adapter<Item>
    private var scrollListener: DataLoadScrollListener? = null
    private var onBackPressed: Long = -1

    override fun doCreateView(savedInstanceState: Bundle?) {
        configToolbar()
        configList()
        configView()
        observes()
    }

    override fun onResume() {
        super.onResume()
        vm.firstPage()
    }

    private fun configToolbar() {
        setSupportActionBar(binding.toolbar)
        // SAMPLE: Bar utils
        BarUtils.setStatusBarLightMode(this, true)
    }

    private fun configList() {
        // SAMPLE: generator adapter
        adapter = getAdapter(R.layout.eyepetizer_tem_home, { helper, item ->
            helper.setText(R.id.tv_title, item.data.title)
            helper.setText(R.id.tv_sub_title, item.data.author?.name + " | " + item.data.category)
            Glide.with(context)
                .load(item.data.cover?.homepage)
                .thumbnail(Glide.with(context).load(R.drawable.recommend_summary_card_bg_unlike))
                .into(helper.getView<View>(R.id.iv_cover) as ImageView)
            // SAMPLE: Glide transformation
            Glide.with(context)
                .load(item.data.author?.icon)
                .apply(RequestOptions().transforms(CenterCrop(),
                    CornersTransformation(20f.dp(), 0, CornersTransformation.CornerType.ALL)))
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
        // SAMPLE: custom recycler scroll listener
        scrollListener = object : DataLoadScrollListener(binding.rv.layoutManager as LinearLayoutManager) {
            override fun loadMore() {
                vm.nextPage()
            }
        }
        binding.rv.addOnScrollListener(scrollListener!!)
        binding.rv.addOnScrollListener(object : CustomRecyclerScrollViewListener() {
            override fun hide() {
                val lp = binding.fab.layoutParams as FrameLayout.LayoutParams
                binding.fab.animate().translationY((binding.fab.height+lp.bottomMargin).toFloat())
                    .setInterpolator(AccelerateInterpolator(2f)).start()
            }

            override fun show() {
                binding.fab.animate().translationY(0f)
                    .setInterpolator(DecelerateInterpolator(2f)).start()
            }
        })
        binding.s.bind(binding.rv)
    }

    private fun configView() {
        binding.fab.onDebouncedClick {
            message(context, "Hello World!").show(supportFragmentManager, "hello-fab")
        }
    }

    private fun observes() {
        observe(HomeBean::class.java, {
            L.d("on success")
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
            scrollListener?.loading = false
        }, {
            L.d("on failed")
            toast(it.message)
            binding.ev.showEmpty()
            scrollListener?.loading = false
        }, {
            L.d("on loading")
            // The udf3 is the flag for loading more.
            // We only need to show loading progress bar for first page.
            if (it.udf3 == false) {
                binding.ev.showLoading()
            }
        })
        setMenu(R.menu.eyepetizer_main) { item ->
            when(item.itemId) {
                R.id.action_input_sample -> {

                }
                R.id.action_vmlib_debug -> {
                    ARouter.getInstance().build("/app/debug").navigation()
                    ActivityUtils.overridePendingTransition(this, ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
                }
            }
        }
        onBack{ back ->
            if (onBackPressed + 2000L > System.currentTimeMillis()) {
                back()
            } else {
                toast("Again to exit")
            }
            onBackPressed = System.currentTimeMillis()
        }
    }
}
