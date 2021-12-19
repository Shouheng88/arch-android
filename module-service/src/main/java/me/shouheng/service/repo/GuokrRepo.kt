package me.shouheng.service.repo

import android.text.TextUtils
import com.google.gson.Gson
import com.jakewharton.disklrucache.DiskLruCache
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmConfiguration
import me.shouheng.api.bean.GuokrNews
import me.shouheng.api.bean.GuokrNewsContent
import me.shouheng.service.net.guokrService
import me.shouheng.utils.stability.L
import me.shouheng.utils.store.PathUtils
import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.task.execute
import java.io.File

/**
 * Guokr repo.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
class GuokrRepo private constructor() {

    /** Realm configuration. */
    private val config = RealmConfiguration.Builder().name("guokr").build()

    /** Get guokr news. */
    fun getNews(
        offset: Int,
        limit: Int,
        success: (bean: GuokrNews) -> Unit,
        fail: (code: String, msg: String) -> Unit
    ) {
        Realm.getInstance(config)
            .where(GuokrNews::class.java)
            .findFirstAsync()
            .addChangeListener(RealmChangeListener<GuokrNews> { t ->
                if (t.isValid) {
                    success(t)
                }
            })
        execute<GuokrNews> {
            task {
                guokrService.getNews(offset, limit).apply {
                    Realm.getInstance(config).executeTransaction {
                        it.insertOrUpdate(this)
                    }
                }
            }
            onSucceed { success(it.data) }
            onFailed { fail(it.code, it.message) }
        }
    }

    /** Get guokr news content. */
    fun getNewsContent(
        id: Int,
        success: (bean: GuokrNewsContent) -> Unit,
        fail: (code: String, msg: String) -> Unit
    ) {
        execute<GuokrNewsContent> {
            resources {
                val cache = getGuokrNewsCache(id)
                if (cache != null) {
                    L.d("Hit cache for $id !")
                    Resources.success(cache)
                } else {
                    val content = guokrService.getGuokrContent(id)
                    addGuokrNewsCache(id, content)
                    Resources.success(content)
                }
            }
            onSucceed { success(it.data) }
            onFailed { fail(it.code, it.message) }
        }
    }

    /** Get guokr cache. */
    private fun getGuokrNewsCache(id: Int): GuokrNewsContent? {
        val cacheDir = File(PathUtils.getExternalAppFilesPath() , "guokr")
        val lruCache = DiskLruCache.open(cacheDir, 1, 1, 1024 * 1024 * 10 /*10M*/)
        val json = lruCache.get("guokr_news_${id}")?.getString(0)?:""
        lruCache.close()
        return if (!TextUtils.isEmpty(json)) {
            Gson().fromJson(json, GuokrNewsContent::class.java)
        } else {
            null
        }
    }

    /** Add guokr cache. */
    private fun addGuokrNewsCache(id: Int, content: GuokrNewsContent) {
        val cacheDir = File(PathUtils.getExternalAppFilesPath() , "guokr")
        val lruCache = DiskLruCache.open(cacheDir, 1, 1, 1024 * 1024 * 10 /*10M*/)
        val contentJson = Gson().toJson(content)
        val editor = lruCache.edit("guokr_news_${id}")
        editor.newOutputStream(0).write(contentJson.toByteArray())
        editor.commit()
        lruCache.close()
    }

    companion object {
        val INSTANCE: GuokrRepo by lazy(
            mode = LazyThreadSafetyMode.SYNCHRONIZED
        ) { GuokrRepo() }
    }
}