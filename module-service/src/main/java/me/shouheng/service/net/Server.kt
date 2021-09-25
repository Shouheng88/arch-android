package me.shouheng.service.net

import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Network access object.
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2019-10-03 11:50
 */
class Server private constructor(baseUrl: String) {

    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .build()

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

    private val retorfit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()

    private val rawRetorfit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    companion object {

        private const val TIMEOUT_IN_SECONDS = 15L

        @Volatile
        private var instance: Server? = null

        /** Initialize server with base url [baseUrl] */
        fun initServer(baseUrl: String) {
            instance = Server(baseUrl)
        }

        /** Get server initialized by [initServer] */
        fun <T> get(api: Class<T>): T {
            checkNotNull(instance) { "You MUST init server at first!!!!" }
            return instance!!.retorfit.create(api)
        }

        /** Get server with base url [baseUrl]. */
        fun <T> get(api: Class<T>, baseUrl: String): T {
            val server = Server(baseUrl)
            return server.retorfit.create(api)
        }
    }
}