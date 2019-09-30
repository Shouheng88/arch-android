package me.shouheng.sample.utils.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import me.shouheng.sample.R;
import me.shouheng.utils.store.PathUtils;

public class TestPathUtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_path_utils);

        setTitle("PathUtils");

        final TextView tv = findViewById(R.id.tv);

        String info = "RootPath : \n" + PathUtils.getRootPath() + "\n" +
                "DataPath : \n" + PathUtils.getDataPath() + "\n" +
                "DownloadCachePath : \n" + PathUtils.getDownloadCachePath() + "\n" +
                "InternalAppDataPath : \n" + PathUtils.getInternalAppDataPath() + "\n" +
                "InternalAppCodeCacheDir : \n" + PathUtils.getInternalAppCodeCacheDir() + "\n" +
                "InternalAppCachePath : \n" + PathUtils.getInternalAppCachePath() + "\n" +
                "InternalAppDbPath : \n" + PathUtils.getInternalAppDbPath() + "\n" +
                "InternalAppFilesPath : \n" + PathUtils.getInternalAppFilesPath() + "\n" +
                "InternalAppSpPath : \n" + PathUtils.getInternalAppSpPath() + "\n" +
                "InternalAppNoBackupFilesPath : \n" + PathUtils.getInternalAppNoBackupFilesPath() + "\n" +
                "ExternalStoragePath : \n" + PathUtils.getExternalStoragePath() + "\n" +
                "ExternalMusicPath : \n" + PathUtils.getExternalMusicPath() + "\n" +
                "ExternalPodcastsPath : \n" + PathUtils.getExternalPodcastsPath() + "\n" +
                "ExternalRingtonesPath : \n" + PathUtils.getExternalRingtonesPath() + "\n" +
                "ExternalAlarmsPath : \n" + PathUtils.getExternalAlarmsPath() + "\n" +
                "ExternalNotificationsPath : \n" + PathUtils.getExternalNotificationsPath() + "\n" +
                "ExternalPicturesPath : \n" + PathUtils.getExternalPicturesPath() + "\n" +
                "ExternalMoviesPath : \n" + PathUtils.getExternalMoviesPath() + "\n" +
                "ExternalDownloadsPath : \n" + PathUtils.getExternalDownloadsPath() + "\n" +
                "ExternalDcimPath : \n" + PathUtils.getExternalDcimPath() + "\n" +
                "ExternalDocumentsPath : \n" + PathUtils.getExternalDocumentsPath() + "\n" +
                "ExternalAppDataPath : \n" + PathUtils.getExternalAppDataPath() + "\n" +
                "ExternalAppDataPath : \n" + PathUtils.getExternalAppCachePath() + "\n" +
                "ExternalAppFilesPath : \n" + PathUtils.getExternalAppFilesPath() + "\n" +
                "ExternalAppMusicPath : \n" + PathUtils.getExternalAppMusicPath() + "\n" +
                "ExternalAppPodcastsPath : \n" + PathUtils.getExternalAppPodcastsPath() + "\n" +
                "ExternalAppRingtonesPath : \n" + PathUtils.getExternalAppRingtonesPath() + "\n" +
                "ExternalAppAlarmsPath : \n" + PathUtils.getExternalAppAlarmsPath() + "\n" +
                "ExternalAppNotificationsPath : \n" + PathUtils.getExternalAppNotificationsPath() + "\n" +
                "ExternalAppPicturesPath : \n" + PathUtils.getExternalAppPicturesPath() + "\n" +
                "ExternalAppMoviesPath : \n" + PathUtils.getExternalAppMoviesPath() + "\n" +
                "ExternalAppDownloadPath : \n" + PathUtils.getExternalAppDownloadPath() + "\n" +
                "ExternalAppDcimPath : \n" + PathUtils.getExternalAppDcimPath() + "\n" +
                "ExternalAppDocumentsPath : \n" + PathUtils.getExternalAppDocumentsPath() + "\n" +
                "ExternalAppObbPath : \n" + PathUtils.getExternalAppObbPath();
        tv.setText(info);
    }
}
