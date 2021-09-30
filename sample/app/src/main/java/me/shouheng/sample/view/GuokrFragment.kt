package me.shouheng.sample.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.api.bean.Result
import me.shouheng.sample.R
import me.shouheng.sample.vm.GuokrViewModel
import me.shouheng.sample.widget.loadGuokrAuthor
import me.shouheng.sample.widget.loadGuokrCover
import me.shouheng.uix.widget.databinding.UixLayoutSimpleListBinding
import me.shouheng.uix.widget.rv.listener.AbsDataLoadListener
import me.shouheng.uix.widget.rv.listener.LinearDataLoadListener
import me.shouheng.utils.ktx.toast
import me.shouheng.vmlib.base.ViewBindingFragment
import me.shouheng.vmlib.component.observeOnList
import me.shouheng.xadapter.createAdapter
import me.shouheng.xadapter.viewholder.onItemClick

/**
 * Guokr fragment
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
class GuokrFragment : ViewBindingFragment<GuokrViewModel, UixLayoutSimpleListBinding>() {

    private val adapter: BaseQuickAdapter<Result, BaseViewHolder> by lazy {
        createAdapter {
            withType(Result::class.java, R.layout.item_guokr_news) {
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
                        toast(it.title?:"")
                    }
                }
            }
        }
    }
    private var dataLoadListener: AbsDataLoadListener? = null

    override fun doCreateView(savedInstanceState: Bundle?) {
        initList()
        observes()
        vm.getNews()
        adapter.setNewData(vm.news)
    }

    private fun initList() {
        binding.rv.adapter = adapter
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        binding.rv.setEmptyView(binding.ev)
        binding.v.bind(binding.rv)
        dataLoadListener = object : LinearDataLoadListener(binding.rv.layoutManager as LinearLayoutManager) {
            override fun loadMore() {
                vm.getNews()
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