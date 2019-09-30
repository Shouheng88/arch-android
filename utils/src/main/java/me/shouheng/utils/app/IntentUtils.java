package me.shouheng.utils.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.List;

import me.shouheng.utils.UtilsApp;

import static android.Manifest.permission.CALL_PHONE;

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/5/7 23:09
 */
public final class IntentUtils {

    /**
     * 判断指定的意图是否有效（有没有 Activity 可以处理它）
     *
     * @param intent 意图
     * @return TRUE  表示可以处理
     */
    public static boolean isIntentAvailable(final Intent intent) {
        return UtilsApp.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    /**
     * 获取匹配指定的意图的全部的 APP 的信息
     *
     * @param intent 意图
     * @return 应用信息列表
     */
    public static List<AppInfo> getMatchAppInfos(Intent intent) {
        PackageManager pm = UtilsApp.getApp().getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        List<AppInfo> appInfoList = new ArrayList<>(infos.size());
        for (ResolveInfo resolveInfo : infos) {
            appInfoList.add(new AppInfo(resolveInfo.activityInfo.packageName,
                    resolveInfo.activityInfo.name,
                    resolveInfo.loadLabel(pm).toString(),
                    resolveInfo.loadIcon(pm)));
        }
        return appInfoList;
    }

    public static Intent getLaunchAppIntent(final String pkgName) {
        return getLaunchAppIntent(pkgName, false);
    }

    /**
     * 返回一个用来启动某个应用的意图
     *
     * @param pkgName   应用的包名
     * @param isNewTask 是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getLaunchAppIntent(final String pkgName, final boolean isNewTask) {
        PackageManager pm = UtilsApp.getApp().getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(pkgName);
        return getIntent(intent, isNewTask);
    }

    public static Intent getLaunchSettingIntent(final String pkgName) {
        return getLaunchSettingIntent(pkgName, false);
    }

    /**
     * 返回一个用来打开某个应用的设置界面的意图
     *
     * @param pkgName 应用的包名
     * @return 意图
     */
    public static Intent getLaunchSettingIntent(final String pkgName, final boolean isNewTask) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + pkgName));
        return getIntent(intent, isNewTask);
    }

    public static Intent getShareTextIntent(final String content) {
        return getShareTextIntent(content, false);
    }

    /**
     * 返回一个用来分享文本的意图
     *
     * @param content   文本内容
     * @param isNewTask 是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getShareTextIntent(final String content, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return getIntent(intent, isNewTask);
    }

    public static Intent getDialIntent(final String phoneNumber) {
        return getDialIntent(phoneNumber, false);
    }

    /**
     * 返回一个用来拨打指定手机号码的意图
     *
     * @param phoneNumber 手机号码
     * @param isNewTask   是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getDialIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    @RequiresPermission(CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber) {
        return getCallIntent(phoneNumber, false);
    }

    /**
     * 返回一个用来拨打指定手机号码的意图
     *
     * @param phoneNumber 手机号码
     * @param isNewTask   是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    @RequiresPermission(CALL_PHONE)
    public static Intent getCallIntent(final String phoneNumber, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        return getIntent(intent, isNewTask);
    }

    public static Intent getSendSmsIntent(final String phoneNumber, final String content) {
        return getSendSmsIntent(phoneNumber, content, false);
    }

    /**
     * 返回一个用来发送短信的意图
     *
     * @param phoneNumber 手机号码
     * @param content     内容
     * @param isNewTask   是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getSendSmsIntent(
            final String phoneNumber, final String content, final boolean isNewTask) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return getIntent(intent, isNewTask);
    }


    public static Intent getSendEmailIntent(
            final String emailAddress, final String subject, final String body) {
        return getSendEmailIntent(emailAddress, subject, body, false);
    }

    /**
     * 返回一个用来发送邮件的意图
     *
     * @param emailAddress 目标邮箱地址
     * @param subject      邮件主题
     * @param body         邮件正文
     * @param isNewTask    是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getSendEmailIntent(final String emailAddress, final String subject,
                                            final String body, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_EMAIL, emailAddress);
        return getIntent(intent, isNewTask);
    }

    public static Intent getOpenWebIntent(final String webUrl) {
        return getOpenWebIntent(webUrl, false);
    }

    /**
     * 返回一个用来打开指定的 url 的意图
     *
     * @param webUrl    网页的 url
     * @param isNewTask 是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getOpenWebIntent(final String webUrl, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
        return getIntent(intent, isNewTask);
    }

    public static Intent getLaunchMarketIntent(final String pkgName) {
        return getLaunchMarketIntent(pkgName, false);
    }

    /**
     * 返回一个用来打开应用商店的意图：优先使用 market 打开，如果没有可用的应用，就使用浏览器打开
     * Google Play 的地址
     *
     * @param pkgName   应用的包名
     * @param isNewTask 是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getLaunchMarketIntent(final String pkgName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pkgName));
        if (!isIntentAvailable(intent)) {
            intent = getOpenWebIntent("https://play.google.com/store/apps/details?id=" + pkgName);
        }
        return getIntent(intent, isNewTask);
    }

    public static Intent getCaptureIntent(final Uri outUri) {
        return getCaptureIntent(outUri, false);
    }

    /**
     * 返回一个用来拍摄图片的意图
     *
     * @param outUri    输出的 uri 的地址
     * @param isNewTask 是否作为 NEW TASK 启动指定的应用
     * @return 意图
     */
    public static Intent getCaptureIntent(final Uri outUri, final boolean isNewTask) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return getIntent(intent, isNewTask);
    }

    public static class AppInfo {

        public final String pkgName;

        public final String launcherName;

        public final String appName;

        public final Drawable launcherIcon;

        public AppInfo(String pkgName, String launcherName, String appName, Drawable launcherIcon) {
            this.pkgName = pkgName;
            this.launcherName = launcherName;
            this.appName = appName;
            this.launcherIcon = launcherIcon;
        }

        @Override
        public String toString() {
            return "AppInfo{" +
                    "pkgName='" + pkgName + '\'' +
                    ", launcherName='" + launcherName + '\'' +
                    ", appName='" + appName + '\'' +
                    ", launcherIcon=" + launcherIcon +
                    '}';
        }
    }

    /*----------------------------------inner methods--------------------------------------*/

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        if (intent == null) return null;
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    private IntentUtils() {
        throw new UnsupportedOperationException("u can't initialize me!");
    }
}
