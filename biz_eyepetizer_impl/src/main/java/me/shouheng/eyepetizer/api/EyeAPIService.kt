package me.shouheng.eyepetizer.api

import me.shouheng.eyepetizer.api.bean.HomeBean
import me.shouheng.common.network.NetworkApi
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Retrofit service for eyepetizer.
 *
 * @author ShouhengWang (shouheng2020@gmail.com)
 * @version 2019/7/6 18:02
 */
interface EyeAPIService {

    @GET("v2/feed?&num=1")
    suspend fun getFirstHomeDataAsync(@Query("date") date: Long?): HomeBean

    @GET
    suspend fun getMoreHomeDataAsync(@Url url: String?): HomeBean
}

/** Retrofit service for eyepetizer. */
val eyeService by lazy(
    mode = LazyThreadSafetyMode.SYNCHRONIZED
) { NetworkApi.INSTANCE.getApi(EyeAPIService::class.java, "http://baobab.kaiyanapp.com/api/") }
