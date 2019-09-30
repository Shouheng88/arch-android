package me.shouheng.sample.utils.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import me.shouheng.sample.R;
import me.shouheng.utils.device.NetworkUtils;

public class TestNetworkUtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_network_utils);

        final TextView tv = findViewById(R.id.tv);

        String info = "isConnected : " + NetworkUtils.isConnected() + "\n" +
                "isAvailableByPing : " + NetworkUtils.isAvailableByPing() + "\n" +
                "isMobileDataEnabled : " + NetworkUtils.isMobileDataEnabled() + "\n" +
                "isMobileData : " + NetworkUtils.isMobileData() + "\n" +
                "is4G : " + NetworkUtils.is4G() + "\n" +
                "isWifiEnabled : " + NetworkUtils.isWifiEnabled() + "\n" +
                "isWifiConnected : " + NetworkUtils.isWifiConnected() + "\n" +
                "isWifiAvailable : " + NetworkUtils.isWifiAvailable() + "\n" +
                "getNetworkOperatorName : " + NetworkUtils.getNetworkOperatorName() + "\n" +
                "getNetworkType : " + NetworkUtils.getNetworkType();
        tv.setText(info);
    }

    public void openWirelessSettings(View view) {
        NetworkUtils.openWirelessSettings();
    }

    /**
     * 目前只有系统 APP 能获取权限：
     * <uses-permission android:name="android.permission.MODIFY_PHONE_STATE" />
     *
     * @param view v
     */
    public void setMobileDataEnabled(View view) {
        NetworkUtils.setMobileDataEnabled(!NetworkUtils.isMobileDataEnabled());
    }

    public void setWifiEnabled(View view) {
        NetworkUtils.setWifiEnabled(!NetworkUtils.isWifiEnabled());
    }
}
