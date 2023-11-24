package me.shouheng.vmlib.network.download;

import java.io.File;

/**
 * The download state listener.
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2019/7/18 22:54
 */
public interface DownloadListener {

    /**
     * Will be called when the download start.
     */
    void onStart();

    /**
     * Will be called when the download progress changed.
     *
     * @param readLength    read content length
     * @param contentLength total content length
     */
    void onProgress(long readLength, long contentLength);

    /**
     * Will be called when the download complete.
     *
     * @param file the result file
     */
    void onComplete(File file);

    /**
     * Will be called when the download error occurred.
     *
     * @param errorCode the error code
     */
    void onError(int errorCode);
}
