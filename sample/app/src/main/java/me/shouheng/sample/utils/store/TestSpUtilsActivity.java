package me.shouheng.sample.utils.store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.shouheng.sample.R;
import me.shouheng.utils.store.SPUtils;

public class TestSpUtilsActivity extends AppCompatActivity {

    private static final String NEW_SP_FILE_NAME = "sp_references";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sp_utils);
    }

    public void writeString2DefaultSP(View view) {
        SPUtils.getInstance().put("default_string", "default_string_value");
    }

    public void writeBoolean2DefaultSP(View view) {
        SPUtils.getInstance().put("default_boolean", true);
    }

    public void writeInteger2DefaultSP(View view) {
        SPUtils.getInstance().put("default_integer", 12);
    }

    public void writeString2SP(View view) {
        SPUtils.getInstance(NEW_SP_FILE_NAME).put("sp_string", "default_string_value");
    }

    public void writeBoolean2SP(View view) {
        SPUtils.getInstance(NEW_SP_FILE_NAME).put("sp_boolean", true);
    }

    public void writeInteger2SP(View view) {
        SPUtils.getInstance(NEW_SP_FILE_NAME).put("sp_integer", 12);
    }
}
