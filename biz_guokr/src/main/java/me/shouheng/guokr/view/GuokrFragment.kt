package me.shouheng.guokr.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.guokr.R
import me.shouheng.guokr.api.bean.Result
import me.shouheng.guokr.vm.GuokrViewModel
import me.shouheng.guokr.widget.loadGuokrAuthor
import me.shouheng.guokr.widget.loadGuokrCover
import me.shouheng.uix.widget.databinding.UixLayoutSimpleListBinding
import me.shouheng.uix.widget.rv.listener.AbsDataLoadListener
import me.shouheng.uix.widget.rv.listener.LinearDataLoadListener
import me.shouheng.utils.ktx.toast
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.anno.FragmentConfiguration
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.comn.ContainerActivity
import me.shouheng.vmlib.component.observeOnList
import me.shouheng.xadapter.createAdapter
import me.shouheng.xadapter.viewholder.onItemClick

/**
 * Guokr fragment
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
@Route(path = "/guokr/entrance")
@FragmentConfiguration(shareViewModel = true)
class GuokrFragment: ViewBindingFragment<GuokrViewModel, UixLayoutSimpleListBinding>() {

    private val adapter: BaseQuickAdapter<Result, BaseViewHolder> by lazy {
        createAdapter {
            withType(Result::class.java, R.layout.guokr_item_news) {
                onBind { helper, item ->
                    helper.setText(R.id.tv_title, item.title)
                    helper.setText(R.id.tv_tags, item.subject?.name)
                    helper.loadGuokrCover(item)
                    helper.loadGuokrAuthor(item)
                    helper.setText(R.id.tv_author, item.author?.nickname)
                    helper.setText(R.id.tv_date, item.date_published)
                }
                onItemClick { adapter, _, position ->
                    (adapter.getItem(position) as? Result)?.let {
                        ContainerActivity.open(GuokrDetailFragment::class.java)
                            .put("news_id", it.id)
                            .launch(requireContext())
                    }
                }
            }
        }
    }
    private var dataLoadListener: AbsDataLoadListener? = null

    override fun doCreateView(savedInstanceState: Bundle?) {
        initList()
        observes()
        vm.getNews(this@GuokrFragment.toString())
        adapter.setNewData(vm.news)
        L.d("Current guokr news size [${vm.news.size}] vm[$vm] activity[$activity] fid[${this@GuokrFragment}]")
    }

    private fun initList() {
        binding.rv.adapter = adapter
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        binding.rv.setEmptyView(binding.ev)
        binding.v.bind(binding.rv)
        dataLoadListener = object : LinearDataLoadListener(binding.rv.layoutManager as LinearLayoutManager) {
            override fun loadMore() {
                vm.getNews(this@GuokrFragment.toString())
            }
        }
        binding.rv.addOnScrollListener(dataLoadListener!!)
    }

    private fun observes() {
        observeOnList(Result::class.java) {
            onSuccess {
                adapter.addData(it.data)
                dataLoadListener?.loading = false
                binding.ev.showEmpty()
            }
            onFail {
                toast(it.message)
                dataLoadListener?.loading = false
                binding.ev.showEmpty()
            }
        }
    }
}