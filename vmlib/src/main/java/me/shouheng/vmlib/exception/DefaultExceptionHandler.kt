package me.shouheng.vmlib.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 * The default exception handler. Singleton instance.
 *
 * @Author wangshouheng
 * @Time 2021/9/25
 */
object DefaultExceptionHandler : ExceptionHandler {

    private const val UNAUTHORIZED          = 401
    private const val FORBIDDEN             = 403
    private const val NOT_FOUND             = 404
    private const val REQUEST_TIMEOUT       = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val SERVICE_UNAVAILABLE   = 503

    private const val NETWORK_ERROR         = -1
    private const val RESULT_PARSE          = -2
    private const val SSL_ERROR             = -3
    private const val TIMEOUT_ERROR         = -4
    private const val UNKNOWN_HOST          = -5

    override fun handleException(t: Throwable): Pair<String, String>? {
        return when(t) {
            is HttpException -> {
                when(t.code()) {
                    UNAUTHORIZED          -> Pair("${t.code()}", "Unauthorized")
                    FORBIDDEN             -> Pair("${t.code()}", "Forbidden")
                    NOT_FOUND             -> Pair("${t.code()}", "404 not found")
                    REQUEST_TIMEOUT       -> Pair("${t.code()}", "Request timeout")
                    INTERNAL_SERVER_ERROR -> Pair("${t.code()}", "Internal server error")
                    SERVICE_UNAVAILABLE   -> Pair("${t.code()}", "Service unavailable")
                    else                  -> Pair("$NETWORK_ERROR", "Network error")
                }
            }
            is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> Pair("$RESULT_PARSE", "Parse error")
            is ConnectException           -> Pair("$NETWORK_ERROR", "Connect error")
            is javax.net.ssl.SSLException -> Pair("$SSL_ERROR", "SSL error")
            is ConnectTimeoutException, is java.net.SocketTimeoutException -> Pair("$TIMEOUT_ERROR", "SSL error")
            is java.net.UnknownHostException -> Pair("$UNKNOWN_HOST", "Unknown host")
            else -> null
        }
    }
}
