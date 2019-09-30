package me.shouheng.utils.biz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 社交相关的工具栏
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-09-30 18:14
 */
public final class SocialUtils {

    /**
     * 发起添加群流程。比如，群号：马克笔记(878285438) 的 key 为： 0HQ8P6rzoNTwpHWHtkYPolgPAvQltMdt
     * 调用 joinQQGroup(0HQ8P6rzoNTwpHWHtkYPolgPAvQltMdt) 即可发起手Q客户端申请加群 马克笔记(878285438)
     *
     * @param context context
     * @param key     由官网生成的 key
     * @return        返回 true 表示呼起手 Q 成功，返回 false 表示呼起失败
     **/
    public boolean joinQQGroup(Context context, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-" +
                "bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手 Q 主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    /*------------------------------------------ Private Region ------------------------------------------*/

    private SocialUtils() {
        throw new UnsupportedOperationException("u can't initialize me....");
    }
}
