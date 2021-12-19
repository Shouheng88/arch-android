package me.shouheng.service.repo

import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration
import me.shouheng.api.bean.HomeBean
import me.shouheng.api.bean.Item
import me.shouheng.service.net.eyeService
import me.shouheng.vmlib.task.execute

/**
 * Repo for eyepetizer. Use memory cache at first, then disk cache
 * (by sp) and last request from network.
 */
class EyeRepo private constructor() {

    /** Memory cache */
    private var beans = mutableListOf<HomeBean>()

    /** Realm configuration. */
    private val config = RealmConfiguration.Builder().name("eyepetizer").build()

    /** Get home bean data of first page */
    fun firstPage(
        date: Long?,
        success: (bean: HomeBean) -> Unit,
        fail: (code: String, msg: String) -> Unit
    ) {
        if (beans.isNotEmpty()) {
            success(beans.first())
            return
        }
        Realm.getInstance(config)
            .where(HomeBean::class.java)
            .findFirstAsync()
            .addChangeListener(RealmChangeListener<HomeBean> { t ->
                if (t.isValid) {
                    beans.add(t)
                    success(t)
                }
            })
        execute<HomeBean> {
            task {
                eyeService.getFirstHomeDataAsync(date).apply {
                    Realm.getInstance(config).executeTransaction {
                        it.insertOrUpdate(this)
                    }
                }
            }
            onSucceed {
                beans.add(it.data)
                success(it.data)
            }
            onFailed { fail(it.code, it.message) }
        }
    }

    /** Get home bean data of more page */
    fun morePage(
        url: String?,
        success: (bean: HomeBean) -> Unit,
        fail: (code: String, msg: String) -> Unit
    ) {
        execute<HomeBean> {
            request { eyeService.getMoreHomeDataAsync(url) }
            onSucceed {
                beans.add(it.data)
                success(it.data)
            }
            onFailed { fail(it.code, it.message) }
        }
    }

    /** Get item by id. */
    fun getItemById(itemId: Int): Item? {
        beans.forEach {
            it.issueList?.forEach { issue ->
                issue.itemList?.forEach { item ->
                    if (item.data?.id == itemId) {
                        return item
                    }
                }
            }
        }
        return null
    }

    companion object {
        val INSTANCE: EyeRepo by lazy(
            mode = LazyThreadSafetyMode.SYNCHRONIZED
        ) { EyeRepo() }
    }
}
