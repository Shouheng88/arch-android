package me.shouheng.sample.utils.app;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import me.shouheng.sample.R;
import me.shouheng.sample.utils.common.BaseActivity;
import me.shouheng.utils.app.AppUtils;

public class TestAppUtilsActivity extends BaseActivity {

    public static final String WEIBO_APP_PACKAGE = "com.sina.weibo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_app_utils);

        TextView tv1 = findViewById(R.id.tv);
        ImageView iv1 = findViewById(R.id.iv1);
        TextView tv2 = findViewById(R.id.tv2);
        ImageView iv2 = findViewById(R.id.iv2);

        String sb = "Current App: " + "\n" +
                "Is app rooted : " + AppUtils.isAppRoot() + "\n" +
                "Is app debug : " + AppUtils.isAppDebug() + "\n" +
                "Is app system : " + AppUtils.isAppSystem() + "\n" +
                "Package name : " + AppUtils.getPackageName() + "\n" +
                "App name : " + AppUtils.getAppName() + "\n" +
                "App version name : " + AppUtils.getAppVersionName() + "\n" +
                "App version code : " + AppUtils.getAppVersionCode() + "\n" +
                "App signature SHA1 : " + AppUtils.getAppSignatureSHA1() + "\n" +
                "App signature SHA256 : " + AppUtils.getAppSignatureSHA256() + "\n" +
                "App signature MD5 : " + AppUtils.getAppSignatureMD5() + "\n" +
                "Meta-Data : " + AppUtils.getMetaData("META_DATA") + "\n" +
                "FirstInstall : " + AppUtils.getAppFirstInstallTime() + "\n" +
                "LastUpdate : " + AppUtils.getAppLastUpdateTime() + "\n" +
                "AppSize : " + AppUtils.getAppSize() + "\n" +
                "AppUid : " + AppUtils.getAppUid() + "\n" +
                "AppSource : " + AppUtils.getAppSourceDir() + "\n" +
                "TargetSdkVersion : " + AppUtils.getAppTargetSdkVersion() + "\n" +
//                "MinSdkVersion : " + AppUtils.getAppMinSdkVersion() + "\n" +
                "\n" + "\n";
        tv1.setText(sb);
        iv1.setImageDrawable(AppUtils.getAppIcon());

        String sb2 = "Weibo App: " + "\n" +
                "Is app debug : " + AppUtils.isAppDebug(WEIBO_APP_PACKAGE) + "\n" +
                "Is app system : " + AppUtils.isAppSystem(WEIBO_APP_PACKAGE) + "\n" +
                "Package name : " + WEIBO_APP_PACKAGE + "\n" +
                "App name : " + AppUtils.getAppName(WEIBO_APP_PACKAGE) + "\n" +
                "App version name : " + AppUtils.getAppVersionName(WEIBO_APP_PACKAGE) + "\n" +
                "App version code : " + AppUtils.getAppVersionCode(WEIBO_APP_PACKAGE) + "\n" +
                "App signature SHA1 : " + AppUtils.getAppSignatureSHA1(WEIBO_APP_PACKAGE) + "\n" +
                "App signature SHA256 : " + AppUtils.getAppSignatureSHA256(WEIBO_APP_PACKAGE) + "\n" +
                "App signature MD5 : " + AppUtils.getAppSignatureMD5(WEIBO_APP_PACKAGE) + "\n" +
                "FirstInstall : " + AppUtils.getAppFirstInstallTime(WEIBO_APP_PACKAGE) + "\n" +
                "LastUpdate : " + AppUtils.getAppLastUpdateTime(WEIBO_APP_PACKAGE) + "\n" +
                "AppSize : " + AppUtils.getAppSize(WEIBO_APP_PACKAGE) + "\n" +
                "AppUid : " + AppUtils.getAppUid(WEIBO_APP_PACKAGE) + "\n" +
                "AppSource : " + AppUtils.getAppSourceDir(WEIBO_APP_PACKAGE) + "\n" +
                "TargetSdkVersion : " + AppUtils.getAppTargetSdkVersion(WEIBO_APP_PACKAGE) + "\n" +
//                "MinSdkVersion : " + AppUtils.getAppMinSdkVersion(WEIBO_APP_PACKAGE) + "\n" +
                "";
        tv2.setText(sb2);
        iv2.setImageDrawable(AppUtils.getAppIcon(WEIBO_APP_PACKAGE));
    }
}
