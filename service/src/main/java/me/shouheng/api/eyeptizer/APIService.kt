package me.shouheng.api.eyeptizer

import io.reactivex.Observable
import me.shouheng.api.bean.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 18:02
 */
interface APIService {

    @GET("v2/feed?&num=1")
    fun getFirstHomeData(@Query("date") date: Long?): Observable<HomeBean>

    @GET
    fun getMoreHomeData(@Url url: String?): Observable<HomeBean>
}