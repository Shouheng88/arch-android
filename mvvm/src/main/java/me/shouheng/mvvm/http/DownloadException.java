package me.shouheng.mvvm.http;

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019/7/18 23:21
 */
public class DownloadException extends RuntimeException {

    public final String errorCode;

    DownloadException(String errorCode) {
        this.errorCode = errorCode;
    }
}
