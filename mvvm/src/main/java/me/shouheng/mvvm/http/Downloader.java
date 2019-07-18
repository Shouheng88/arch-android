package me.shouheng.mvvm.http;

/**
 * The downloader used to download files from network
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 */
public final class Downloader {

    private static Downloader downloader;



    public static Downloader getInstance() {
        if (downloader == null) {
            synchronized (Downloader.class) {
                if (downloader == null) {
                    downloader = new Downloader();
                }
            }
        }
        return downloader;
    }

    private Downloader() {
    }
}
