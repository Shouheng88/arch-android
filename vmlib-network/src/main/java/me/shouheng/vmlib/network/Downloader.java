package me.shouheng.vmlib.network;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import me.shouheng.vmlib.network.interceptor.ProgressInterceptor;
import me.shouheng.vmlib.network.interceptor.ProgressResponseCallback;
import me.shouheng.utils.device.NetworkUtils;
import me.shouheng.utils.stability.L;
import me.shouheng.utils.store.IOUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * The downloader used to download files from network. Sample
 *
 * <code>
 * Downloader.getInstance()
 *     .setOnlyWifi(true)
 *     .download(downloadUrl, PathUtils.getExternalStoragePath(), object : DownloadListener {
 *         override fun onError(errorCode: Int) {
 *             showShort("Download : error $errorCode")
 *         }
 *
 *         override fun onStart() {
 *             showShort("Download : start")
 *         }
 *
 *         override fun onProgress(readLength: Long, contentLength: Long) {
 *             L.d("Download : onProgress $readLength/$contentLength")
 *         }
 *
 *         override fun onComplete(file: File?) {
 *             showShort("Download : complete : ${file?.absoluteFile}")
 *         }
 *     })
 * </code>
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 */
public final class Downloader {

    /** Error code: the network is unavailable.*/
    public static final int ERROR_CODE_NETWORK_UNAVAILABLE  = 100001;
    /** Error code: only wifi and the wifi is unavailable.*/
    public static final int ERROR_CODE_WIFI_UNAVAILABLE     = 100002;
    /** Error code: no response body for request.*/
    public static final int ERROR_CODE_NO_RESPONSE_BODY     = 100003;
    /** Error code: failed to write file to file system. */
    public static final int ERROR_CODE_IO                   = 100004;
    /** Error code: other network error*/
    public static final int ERROR_CODE_NETWORK              = 100005;

    /** Seconds to timeout when download*/
    private static final int TIME_OUT_SECONDS = 20;

    private DownloadListener downloadListener;

    private OkHttpClient okHttpClient;
    private Call requestCall;
    private boolean onlyWifi;
    private String url;
    private String filePath;
    private String fileName;
    private Handler mainThreadHandler;

    public static Downloader getInstance() {
        return new Downloader();
    }

    /**
     * Get file name from url, might be null if failed to parse url.
     *
     * @param imgUrl image url of string
     * @return       image url
     */
    @Nullable
    public static String getFileName(String imgUrl) {
        try {
            URL url = new URL(imgUrl);
            return new File(url.getFile()).getName();
        } catch (MalformedURLException ex) {
            L.e(ex);
            return null;
        }
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
        mainThreadHandler = new Handler(Looper.getMainLooper());
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
     * Download file of given url. The program will get file name from url.
     *
     * @param url              the remote url
     * @param filePath         the file path that the file was saved to
     * @param downloadListener the download progress callback
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET, ACCESS_NETWORK_STATE, WRITE_EXTERNAL_STORAGE})
    public void download(@NonNull String url, @NonNull String filePath, @Nullable DownloadListener downloadListener) {
        this.download(url, filePath, getFileName(url), downloadListener);
    }

    /**
     * Download file of given url.
     *
     * @param url              the url
     * @param filePath         the file path to save file
     * @param fileName         the file name of downloaded file
     * @param downloadListener the download state callback
     */
    @RequiresPermission(allOf = {ACCESS_WIFI_STATE, INTERNET, ACCESS_NETWORK_STATE, WRITE_EXTERNAL_STORAGE})
    public void download(@NonNull String url, @NonNull String filePath, @Nullable String fileName, @Nullable DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
        this.url = url;
        this.filePath = filePath;
        if (fileName == null) L.w("The parameter 'fileName' was null, timestamp will be used.");
        this.fileName = fileName == null ? String.valueOf(System.currentTimeMillis()) : fileName;

        if (!NetworkUtils.isConnected()) {
            notifyDownloadError(ERROR_CODE_NETWORK_UNAVAILABLE);
        } else {
            if (onlyWifi) {
                checkWifiAvailable();
            } else {
                doDownload();
            }
        }
    }

    /**
     * Cancel the request
     */
    public void cancel() {
        if (requestCall != null && !requestCall.isCanceled()) {
            requestCall.cancel();
        }
    }

    /*--------------------------------------------inner methods-------------------------------------------*/

    /**
     * Check wifi availability in background thread,
     * since it might block the ui thread.
     */
    private void checkWifiAvailable() {
        new WifiChecker(new WifiChecker.WifiStateListener() {
            @Override
            public void onGetWifiState(boolean available) {
                if (available) {
                    doDownload();
                } else {
                    notifyDownloadError(ERROR_CODE_WIFI_UNAVAILABLE);
                }
            }
        }).execute();
    }

    private void doDownload() {
        notifyDownloadStart();
        requestCall = okHttpClient.newCall(
                new Request.Builder()
                        .url(url)
                        .build()
        );
        requestCall.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                notifyDownloadError(ERROR_CODE_NETWORK);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody body = response.body();
                try {
                    InputStream is;
                    if (body != null) {
                        is = body.byteStream();
                    } else {
                        notifyDownloadError(ERROR_CODE_NO_RESPONSE_BODY);
                        return;
                    }
                    File fileSaveTo = new File(filePath, fileName);
                    boolean succeed = IOUtils.writeFileFromIS(fileSaveTo, is);
                    if (!succeed) {
                        notifyDownloadError(ERROR_CODE_IO);
                    } else {
                        notifyDownloadComplete(fileSaveTo);
                    }
                } catch (Exception ex) {
                    notifyDownloadError(ERROR_CODE_IO);
                }
            }
        });
    }

    private void notifyDownloadStart() {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener != null) {
                    downloadListener.onStart();
                }
            }
        });
    }

    private void notifyDownloadComplete(final File fileSaveTo) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener != null) {
                    downloadListener.onComplete(fileSaveTo);
                }
            }
        });
    }

    private void notifyDownloadError(final int errorCode) {
        mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (downloadListener != null) {
                    downloadListener.onError(errorCode);
                }
            }
        });
    }

    private static class WifiChecker extends AsyncTask<Void, Void, Boolean> {

        private WifiStateListener wifiStateListener;

        WifiChecker(WifiStateListener wifiStateListener) {
            this.wifiStateListener = wifiStateListener;
        }

        @SuppressLint("MissingPermission")
        @Override
        protected Boolean doInBackground(Void... voids) {
            return NetworkUtils.isWifiAvailable();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (wifiStateListener != null) {
                wifiStateListener.onGetWifiState(aBoolean);
            }
        }

        public interface WifiStateListener {
            void onGetWifiState(boolean available);
        }
    }
}
