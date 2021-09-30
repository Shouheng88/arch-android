package me.shouheng.service.api

import me.shouheng.api.bean.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Retrofit service for eyepetizer.
 *
 * @author ShouhengWang (shouheng2020@gmail.com)
 * @version 2019/7/6 18:02
 */
interface EyeService {

    @GET("v2/feed?&num=1")
    suspend fun getFirstHomeDataAsync(@Query("date") date: Long?): HomeBean

    @GET
    suspend fun getMoreHomeDataAsync(@Url url: String?): HomeBean
}
