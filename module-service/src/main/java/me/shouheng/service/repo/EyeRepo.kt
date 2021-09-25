package me.shouheng.service.repo

import android.text.TextUtils
import com.google.gson.Gson
import me.shouheng.api.bean.HomeBean
import me.shouheng.service.api.EyeService
import me.shouheng.service.net.Net
import me.shouheng.service.net.Server
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.SPUtils
import me.shouheng.vmlib.task.executeSuspend

class EyeRepo private constructor() {

    /** Memory cache */
    private var bean: HomeBean? = null

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
        val json = SPUtils.get().getString("__data__")
        if (!TextUtils.isEmpty(json)) {
            val bean = Gson().fromJson<HomeBean>(json, HomeBean::class.java)
            L.d("using cache")
            success(bean)
        }
        executeSuspend<HomeBean> {
            execute {
                Net.connectResources(Server.get(EyeService::class.java)
                    .getFirstHomeDataAsync(date))
                    .apply {
                        if (isSucceed) SPUtils.get().put("__data__", Gson().toJson(data))
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
        executeSuspend<HomeBean> {
            execute {
                Net.connectResources(Server.get(EyeService::class.java).getMoreHomeDataAsync(url))
            }
            onSucceed { success(it.data) }
            onFailed { fail(it.code, it.message) }
        }
    }

    companion object {
        val INSTANCE: EyeRepo by lazy { EyeRepo() }
    }
}
