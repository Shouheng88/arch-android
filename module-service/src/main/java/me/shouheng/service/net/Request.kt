package me.shouheng.service.net

import android.util.Log
import com.google.gson.*
import me.shouheng.service.api.EyeService
import me.shouheng.service.api.GuokrService
import me.shouheng.vmlib.network.request.AbsNetworkApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

/** Retrofit service for eyepetizer. */
val eyeService by lazy(
    mode = LazyThreadSafetyMode.SYNCHRONIZED
) { NetworkApi.INSTANCE.getApi(EyeService::class.java, "http://baobab.kaiyanapp.com/api/") }

/** Retrofit service for guokr */
val guokrService by lazy (
    mode = LazyThreadSafetyMode.SYNCHRONIZED
) {
    NetworkApi.INSTANCE.getApi(GuokrService::class.java, "http://apis.guokr.com/minisite/")
}

/** Network api. */
class NetworkApi: AbsNetworkApi() {

    companion object {
        val INSTANCE: NetworkApi by lazy(
            mode = LazyThreadSafetyMode.SYNCHRONIZED
        ) { NetworkApi() }
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
            override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date {
                return Date(json!!.asLong)
            }
        })
        .registerTypeAdapter(Date::class.java, object : JsonSerializer<Date> {
            override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                return JsonPrimitive(src!!.time)
            }
        })
        .create()

    override fun initRetrofit(baseUrl: String, builder: Retrofit.Builder) {
        builder.addConverterFactory(GsonConverterFactory.create(gson))
    }

    override fun initOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(getHttpLoggingInterceptor())
            .build()
    }

    /** Get the http logging interceptor. */
    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                // Use the default log instead of L to avoid break of log.
                Log.d("Network:", message)
            }
        }).apply {
            setLevel(HttpLoggingInterceptor.Level.HEADERS)
        }
    }
}
