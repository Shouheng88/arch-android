package me.shouheng.vmlib.network.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * Abstract retrofit manager.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
abstract class AbsNetworkApi {

    private val okHttpClient: OkHttpClient by lazy {
        initOkHttpClient()
    }

    private val retrofits = mutableMapOf<String, Retrofit>()

    /** Get api for retrofit based on service class and base url. */
    fun <T> getApi(service: Class<T>, baseUrl: String): T {
        var retrofit: Retrofit?
        retrofit = retrofits[baseUrl]
        if (retrofit == null) {
            synchronized(retrofits) {
                retrofit = retrofits[baseUrl]
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(okHttpClient).apply {
                            initRetrofit(baseUrl, this)
                        }
                        .build()
                    retrofits[baseUrl] = retrofit!!
                }
            }
        }
        return retrofit!!.create(service)
    }

    /** Initialize the okhttp client. Override this method to add your own implementation. */
    protected open fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    /** Initialize the retrofit. Override this method to add your custom to retrofit. */
    protected open fun initRetrofit(baseUrl: String, builder: Retrofit.Builder) {

    }
}
