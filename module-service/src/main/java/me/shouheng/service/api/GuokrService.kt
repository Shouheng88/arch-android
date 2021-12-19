package me.shouheng.service.api

import me.shouheng.api.bean.GuokrNews
import me.shouheng.api.bean.GuokrNewsContent
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Guokr retrofit service
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
interface GuokrService {

    @GET("article.json?retrieve_type=by_minisite")
    suspend fun getNews(@Query("offset") offset: Int, @Query("limit") limit: Int): GuokrNews

    @GET("article/{id}.json")
    suspend fun getGuokrContent(@Path("id") id: Int): GuokrNewsContent
}