package me.shouheng.mvvm.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import me.shouheng.mvvm.http.interceptor.ProgressInterceptor;
import me.shouheng.mvvm.http.interceptor.ProgressResponseCallback;
import me.shouheng.utils.device.NetworkUtils;
import me.shouheng.utils.store.IOUtils;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.*;

/**
 * The downloader used to download files from network
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
public final class Downloader {

    /**
     * Error code: the network is unavailable.
     */
    public static final String ERROR_CODE_NETWORK_UNAVAILABLE   = "100001";

    /**
     * Error code: only wifi and the wifi is unavailable.
     */
    public static final String ERROR_CODE_WIFI_UNAVAILABLE      = "100002";

    /**
     * Error code: no response body for request.
     */
    public static final String ERROR_CODE_NO_RESPONSE_BODY      = "100003";

    /**
     * Error code: failed to write file to file system.
     */
    public static final String ERROR_CODE_FAILED_TO_WRITE       = "100004";

    /**
     * Seconds to timeout when download
     */
    private static final int TIME_OUT_SECONDS = 20;

    private DownloadListener downloadListener;

    private OkHttpClient okHttpClient;

    private boolean onlyWifi;

    private String url;

    private File fileSaveTo;

    public static Downloader getInstance() {
        return new Downloader();
    }

    private Downloader() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor(new ProgressResponseCallback() {
                    @Override
                    public void onProgressChanged(long contentLength, long readLength) {
                        if (downloadListener != null) {
                            downloadListener.onProgress(readLength, contentLength);
                        }
                    }
                }))
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Set download only in wifi environment
     *
     * @param onlyWifi only wifi env
     * @return         the downloader
     */
    public Downloader setOnlyWifi(boolean onlyWifi) {
        this.onlyWifi = onlyWifi;
        return this;
    }

    /**
     * Download file of given url.
     *
     * @param url              the remote url
     * @param downloadListener the download progress callback
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET, ACCESS_NETWORK_STATE, WRITE_EXTERNAL_STORAGE})
    public void download(@NonNull String url, @NonNull File fileSaveTo, @Nullable DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        this.url = url;
        this.fileSaveTo = fileSaveTo;

        if (!NetworkUtils.isConnected()) {
            notifyDownloadError(new DownloadException(ERROR_CODE_NETWORK_UNAVAILABLE));
        } else if (onlyWifi && !NetworkUtils.isWifiAvailable()) {
            notifyDownloadError(new DownloadException(ERROR_CODE_WIFI_UNAVAILABLE));
        } else {
            doDownload();
        }
    }

    /*--------------------------------------------inner methods-------------------------------------------*/

    private void doDownload() {
        notifyDownloadStart();
        okHttpClient.newCall(
                new Request.Builder()
                        .url(url)
                        .build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                notifyDownloadError(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody body = response.body();
                try {
                    InputStream is = null;
                    if (body != null) {
                        is = body.byteStream();
                    } else {
                        notifyDownloadError(new DownloadException(ERROR_CODE_NO_RESPONSE_BODY));
                    }
                    boolean succeed = IOUtils.writeFileFromIS(fileSaveTo, is);
                    if (!succeed) {
                        notifyDownloadError(new DownloadException(ERROR_CODE_FAILED_TO_WRITE));
                    } else {
                        notifyDownloadComplete();
                    }
                } catch (Exception ex) {
                    notifyDownloadError(ex);
                }
            }
        });
    }

    private void notifyDownloadStart() {
        if (downloadListener != null) {
            downloadListener.onStart();
        }
    }

    private void notifyDownloadComplete() {
        if (downloadListener != null) {
            downloadListener.onComplete(fileSaveTo);
        }
    }

    private void notifyDownloadError(Exception ex) {
        if (downloadListener != null) {
            downloadListener.onError(ex);
        }
    }
}
