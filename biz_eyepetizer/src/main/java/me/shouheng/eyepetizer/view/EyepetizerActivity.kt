package me.shouheng.eyepetizer.view

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.eyepetizer.R
import me.shouheng.eyepetizer.api.bean.Item
import me.shouheng.eyepetizer.databinding.EyepetizerActivityEyepetizerBinding
import me.shouheng.eyepetizer.vm.EyepetizerViewModel
import me.shouheng.eyepetizer.widget.loadCover
import me.shouheng.eyepetizer.widget.loadRoundImage
import me.shouheng.uix.widget.rv.listener.AbsDataLoadListener
import me.shouheng.uix.widget.rv.listener.LinearDataLoadListener
import me.shouheng.uix.widget.rv.listener.ScrollDisplayListener
import me.shouheng.utils.app.ActivityUtils
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ktx.dp2px
import me.shouheng.utils.ktx.onDebouncedClick
import me.shouheng.utils.ktx.stringOf
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.stability.L
import me.shouheng.utils.ui.BarUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.ViewBindingActivity
import me.shouheng.vmlib.component.LiveDataObserverBuilder
import me.shouheng.vmlib.component.observeOnList
import me.shouheng.xadapter.createAdapter
import me.shouheng.xadapter.viewholder.onItemClick

/** EyepetizerActivity is the viewbinding styled activity. @author Shouheng Wang */
@Route(path = "/eyepetizer/main")
@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_BACK)
class EyepetizerActivity : ViewBindingActivity<EyepetizerViewModel, EyepetizerActivityEyepetizerBinding>() {

    private lateinit var adapter: BaseQuickAdapter<Item, BaseViewHolder>
    private var dataLoadListener: AbsDataLoadListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        L.e("EyepetizerActivity#onCreate $this $vm test[${savedInstanceState?.getString("test")}]")
    }

    override fun onStart() {
        super.onStart()
        L.e("EyepetizerActivity#onStart $this $vm")
    }

    override fun onPause() {
        super.onPause()
        L.e("EyepetizerActivity#onPause $this $vm")
    }

    override fun onStop() {
        super.onStop()
        L.e("EyepetizerActivity#onStop $this $vm")
    }

    override fun onDestroy() {
        super.onDestroy()
        L.e("EyepetizerActivity#onDestroy $this $vm")
    }

    override fun doCreateView(savedInstanceState: Bundle?) {
        configToolbar()
        configList()
        configView()
        observes()
    }

    override fun onResume() {
        super.onResume()
        vm.firstPage()
        L.e("EyepetizerActivity#onResume $this $vm")
    }

    private fun configToolbar() {
        BarUtils.setStatusBarLightMode(this, true)
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = stringOf(R.string.eye_app_name)
    }

    /**
     * If we use the android:configChanges="orientation|keyboardHidden|screenSize" configuration
     * in the manifest, then we will receive the [onConfigurationChanged] callback. In this way,
     * the activity won't recreate. So, we can only change the layout in this method.
     * But, no matter the activity is recreated or not, the view model won't be recreated.
     *
     * SINCE THE VIEWMODEL IS BIND WITH FRAGMENT WHO IS SET [Fragment.setRetainInstance] IS TRUE.
     * SO THE VIEWMODEL IS SHARED TO NEW ACTIVITY WHEN RECREATED. (OLD VERSION).
     * NOW IT'S USING THE [getLastNonConfigurationInstance] TO TRANSFER THE STATE BETWEEN DIFFERENT
     * VIEWMODEL WHEN CONFIGURATION CHANGED.
     *
     * IF THE ACTIVITY IS DESTROYED BACKGROUND, BOTH THE ACTIVITY AND VIEWMODEL WILL BE CREATED.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val landscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val lm = if (landscape) GridLayoutManager(context, 2) else LinearLayoutManager(context)
        binding!!.rv.layoutManager = lm
    }

    private fun configList() {
        val landscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val lm = if (landscape) GridLayoutManager(context, 2) else LinearLayoutManager(context)
        adapter = createAdapter {
            withType(Item::class.java, R.layout.eyepetizer_item_home) {
                onBind { helper, item ->
                    helper.setText(R.id.tv_title, item.data?.title)
                    helper.setText(R.id.tv_sub_title, item.data?.author?.name + " | " + item.data?.category)
                    helper.loadCover(context,
                        R.id.iv_cover, item.data?.cover?.homepage,
                        R.drawable.eyepetizer_card_bg_unlike
                    )
                    helper.loadRoundImage(context,
                        R.id.iv_author, item.data?.author?.icon,
                        R.mipmap.eyepetizer, 20f.dp2px())
                }
                this.onItemClick { _, _, position ->
                    val itemList = adapter.data[position]
                    ARouter.getInstance().build("/eyepetizer/details")
                        .withInt(VideoDetailActivity.EXTRA_ITEM_DATA_ID, itemList.data?.id?:0)
                        .navigation()
                    ActivityUtils.overridePendingTransition(activity, ActivityDirection.ANIMATE_SLIDE_TOP_FROM_BOTTOM)
                }
            }
        }
        binding?.rv?.layoutManager = lm
        binding?.rv?.adapter = adapter
        dataLoadListener = object : LinearDataLoadListener(lm) {
            override fun loadMore() {
                vm.nextPage()
            }
        }
        binding?.rv?.addOnScrollListener(dataLoadListener!!)
        binding?.rv?.addOnScrollListener(object : ScrollDisplayListener() {
            override fun display(show: Boolean) {
                if (show) {
                    binding!!.fab.animate().translationY(0f)
                        .setInterpolator(DecelerateInterpolator(2f)).start()
                } else {
                    val lp = binding!!.fab.layoutParams as FrameLayout.LayoutParams
                    binding!!.fab.animate().translationY((binding!!.fab.height+lp.bottomMargin).toFloat())
                        .setInterpolator(AccelerateInterpolator(2f)).start()
                }
            }
        })
        binding?.s?.bind(binding!!.rv)
        binding?.rv?.setEmptyView(binding!!.ev)
        // Use the cached value in viewmodel.
        adapter.setNewData(vm.items)
        L.i("Last vm [$vm] and data size [${vm.items.size}]")
    }

    private fun configView() {
        binding!!.fab.onDebouncedClick {
            toast(R.string.eye_fab_clicked)
        }
    }

    /**
     * The kotlin DSL styled observe method.
     *
     * 1.对于分页场景：建议自己维护数据，比如 [EyepetizerViewModel.items]，然后在 oncreate 的时候塞值
     * 观察的时候使用 [LiveDataObserverBuilder.withSticky] 并设置 false 注册非粘性观察。
     * 2.对于非分页场景：建议直接使用 [LiveDataObserverBuilder.withSticky] 并设置为 true 直接获取数据即可。
     */
    private fun observes() {
        observeOnList(Item::class.java) {
            withSticky(false)
            onSuccess {
                L.d("On eyepetizer request succeed!")
                // Set new data if loading the first page, or append to list.
                if (it.udf3 == false) {
                    adapter.setNewData(it.data)
                } else {
                    adapter.addData(it.data)
                }
                binding?.ev?.showEmpty()
                dataLoadListener?.loading = false
            }
            onLoading {
                L.d("On eyepetizer requesting!")
                // The udf3 is the flag for loading more.
                // We only need to show loading progress bar for first page.
                if (it.udf3 == false) {
                    binding?.ev?.showLoading()
                }
            }
            onFail {
                L.d("On eyepetizer request failed!")
                toast(it.message)
                binding?.ev?.showEmpty()
                dataLoadListener?.loading = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putString("test", "test_value")
    }
}
