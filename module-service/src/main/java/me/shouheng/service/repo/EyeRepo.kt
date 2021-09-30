package me.shouheng.service.repo

import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration
import me.shouheng.api.bean.HomeBean
import me.shouheng.service.net.eyeService
import me.shouheng.vmlib.task.execute

/**
 * Repo for eyepetizer. Use memory cache at first, then disk cache
 * (by sp) and last request from network.
 */
class EyeRepo private constructor() {

    /** Memory cache */
    private var bean: HomeBean? = null

    /** Realm configuration. */
    private val config = RealmConfiguration.Builder().name("eyepetizer").build()

    /** Get home bean data of first page */
    fun firstPage(
        date: Long?,
        success: (bean: HomeBean) -> Unit,
        fail: (code: String, msg: String) -> Unit
    ) {
        if (bean != null) {
            success(bean!!)
            return
        }
        Realm.getInstance(config)
            .where(HomeBean::class.java)
            .findFirstAsync()
            .addChangeListener(RealmChangeListener<HomeBean> { t ->
                if (t.isValid) {
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
            onSucceed { success(it.data) }
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
            onSucceed { success(it.data) }
            onFailed { fail(it.code, it.message) }
        }
    }

    companion object {
        val INSTANCE: EyeRepo by lazy(
            mode = LazyThreadSafetyMode.SYNCHRONIZED
        ) { EyeRepo() }
    }
}
