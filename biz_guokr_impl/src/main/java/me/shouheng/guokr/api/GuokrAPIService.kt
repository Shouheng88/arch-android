package me.shouheng.guokr.api

import me.shouheng.common.network.NetworkApi
import me.shouheng.guokr.api.bean.GuokrNews
import me.shouheng.guokr.api.bean.GuokrNewsContent
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Guokr retrofit service
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
interface GuokrAPIService {

    @GET("article.json?retrieve_type=by_minisite")
    suspend fun getNews(@Query("offset") offset: Int, @Query("limit") limit: Int): GuokrNews

    @GET("article/{id}.json")
    suspend fun getGuokrContent(@Path("id") id: Int): GuokrNewsContent
}

/** Retrofit service for guokr */
val guokrService by lazy (
    mode = LazyThreadSafetyMode.SYNCHRONIZED
) {
    NetworkApi.INSTANCE.getApi(GuokrAPIService::class.java, "http://apis.guokr.com/minisite/")
}