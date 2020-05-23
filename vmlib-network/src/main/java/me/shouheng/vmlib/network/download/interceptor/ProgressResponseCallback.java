package me.shouheng.vmlib.network.download.interceptor;

/**
 * The callback for progress response.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019/7/18 22:48
 */
public interface ProgressResponseCallback {

    /**
     * On response progress change callback method.
     *
     * @param contentLength the content length
     * @param readLength    the read content length
     */
    void onProgressChanged(long contentLength, long readLength);
}
