package me.shouheng.api.eyeptizer

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/6 18:07
 */
object APIRetrofit {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://baobab.kaiyanapp.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    val eyepetizer: APIService
        get() = retrofit.create(APIService::class.java)

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
}