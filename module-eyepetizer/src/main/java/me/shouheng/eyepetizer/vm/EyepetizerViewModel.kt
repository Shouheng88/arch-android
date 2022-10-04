package me.shouheng.eyepetizer.vm

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.bean.Item
import me.shouheng.api.service.EyepetizerService
import me.shouheng.api.service.OnGetHomeBeansListener
import me.shouheng.utils.stability.L
import me.shouheng.vmlib.base.BaseViewModel

/**
 * Eyepetizer viewmodel.
 *
 * @author ShouhengWang (shouheng2020@gmail.com)
 * @version 2019/7/6 18:19
 */
class EyepetizerViewModel(application: Application) : BaseViewModel(application) {

    private var firstPageRequested: Boolean = false

    /** Micro service styled method invocation of [ARouter]. */
    private val eyeService: EyepetizerService = ARouter.getInstance().navigation(EyepetizerService::class.java)

    private var nextPageUrl: String? = null

    /**
     * We use the value in viewmodel to store the cached value. So when the activity's
     * configuration is changed, we can use the cached value. Methods [setListSuccess]
     * here just performs to transfer data between viewmodel and view layer. If we store
     * everything in list, it may be confusion to customer. If we only save each request
     * result in livedata, then we can only keep the last request when observe next time.
     */
    val items = mutableListOf<Item>()

    /** Request the first page. */
    fun firstPage() {
        if (firstPageRequested) return
        firstPageRequested = true
        setListLoading(Item::class.java)
        eyeService.getFirstHomePage(null, object : OnGetHomeBeansListener {
            override fun onGetHomeBean(data: HomeBean) {
                L.d("Got home bean")
                nextPageUrl = data.nextPageUrl
                val its = getItems(data)
                items.clear()
                items.addAll(its)
                setListSuccess(Item::class.java, its, udf3 = false)
            }

            override fun onError(code: String, msg: String) {
                L.d("Got home bean error")
                setListFailure(Item::class.java, code, msg)
            }
        })
    }

    /** Request the next page. */
    fun nextPage() {
        setListLoading(Item::class.java, udf3 = true)
        eyeService.getMoreHomePage(nextPageUrl, object : OnGetHomeBeansListener {
            override fun onGetHomeBean(data: HomeBean) {
                L.d("Got next page home bean")
                nextPageUrl = data.nextPageUrl
                val its = getItems(data)
                items.addAll(its)
                setListSuccess(Item::class.java, its, udf3 = true)
            }

            override fun onError(code: String, msg: String) {
                L.d("Got next page home bean error")
                setListFailure(Item::class.java, code, msg, udf3 = true)
            }
        })
    }

    /** Get items from homebean. */
    private fun getItems(homeBean: HomeBean): List<Item> {
        val list = mutableListOf<Item>()
        homeBean.issueList?.forEach { issue ->
            issue.itemList?.forEach { item ->
                if (item.data?.cover != null
                    && item.data?.author != null
                ) list.add(item)
            }
        }
        return list
    }
}