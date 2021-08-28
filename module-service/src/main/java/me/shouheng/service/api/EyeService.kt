package me.shouheng.service.api

import kotlinx.coroutines.Deferred
import me.shouheng.api.bean.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @author WngShhng (shouheng2020@gmail.com)
 * @version 2019/7/6 18:02
 */
interface EyeService {

    @GET("v2/feed?&num=1")
    fun getFirstHomeDataAsync(@Query("date") date: Long?): Deferred<HomeBean>

    @GET
    fun getMoreHomeDataAsync(@Url url: String?): Deferred<HomeBean>
}