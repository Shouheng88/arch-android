package me.shouheng.service.repo

import android.text.TextUtils
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.shouheng.api.bean.HomeBean
import me.shouheng.service.api.EyeService
import me.shouheng.service.net.Net
import me.shouheng.service.net.Server
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.SPUtils

class EyepetizerRepo private constructor() {

    /** Memory cache */
    private var bean: HomeBean? = null

    /** Get home bean data of first page */
    fun firstPage(date: Long?, success: (bean: HomeBean) -> Unit, fail: (code: String, msg: String) -> Unit) {
        if (bean != null) {
            success(bean!!)
            return
        }
        val json = SPUtils.get().getString("__data__")
        if (!TextUtils.isEmpty(json)) {
            val bean = Gson().fromJson<HomeBean>(json, HomeBean::class.java)
            L.d("using cache")
            success(bean)
            return
        }
        GlobalScope.launch(Dispatchers.Main) {
            val res = withContext(Dispatchers.IO) {
                val res = Net.connectResources(Server.get(EyeService::class.java)
                    .getFirstHomeDataAsync(date))
                if (res.isSucceed) {
                    SPUtils.get().put("__data__", Gson().toJson(res.data))
                }
                res
            }
            if (res.isSucceed) {
                success(res.data)
            } else {
                fail(res.code, res.message)
            }
        }
    }

    /** Get home bean data of more page */
    fun morePage(url: String?, success: (bean: HomeBean) -> Unit, fail: (code: String, msg: String) -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            val res = withContext(Dispatchers.IO) {
                Net.connectResources(Server.get(EyeService::class.java)
                    .getMoreHomeDataAsync(url))
            }
            if (res.isSucceed) {
                success(res.data)
            } else {
                fail(res.code, res.message)
            }
        }
    }

    companion object {
        val instance: EyepetizerRepo by lazy { EyepetizerRepo() }
    }
}
