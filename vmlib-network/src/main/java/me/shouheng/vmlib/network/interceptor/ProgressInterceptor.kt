package me.shouheng.vmlib.network.interceptor

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * The http progress interceptor.
 *
 * @author [WngShhng](mailto:shouheng2020@gmail.com)
 * @version 2019/7/18 22:43
 */
class ProgressInterceptor(private val callback: ProgressResponseCallback) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .body(ProgressResponseBody(response.body!!, callback))
            .build()
    }
}

/** The callback for progress response.*/
interface ProgressResponseCallback {
    /**
     * On response progress change callback method.
     *
     * @param contentLength the content length
     * @param readLength    the read content length
     */
    fun onProgressChanged(contentLength: Long, readLength: Long)
}

/** The decorator implementation for ResponseBody.*/
class ProgressResponseBody internal constructor(
    private val body: ResponseBody,
    private val callback: ProgressResponseCallback?
) : ResponseBody() {

    private var bufferedSource: BufferedSource? = null

    override fun contentType(): MediaType? = body.contentType()

    override fun contentLength(): Long = body.contentLength()

    override fun source(): BufferedSource = bufferedSource ?: source(body.source()).buffer()

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {

            var readLength: Long = 0

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                readLength += if (bytesRead == -1L) 0 else bytesRead
                callback?.onProgressChanged(contentLength(), readLength)
                return bytesRead
            }
        }
    }

}