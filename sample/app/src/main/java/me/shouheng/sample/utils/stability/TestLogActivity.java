package me.shouheng.sample.utils.stability;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import me.shouheng.sample.R;
import me.shouheng.sample.utils.common.BaseActivity;
import me.shouheng.utils.stability.LogUtils;

public class TestLogActivity extends BaseActivity {

    private int cnt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_test);
        EditText et = findViewById(R.id.et);
        findViewById(R.id.btn_log).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt++;
                switch (cnt % 6) {
                    case 0:
                        LogUtils.v("Logging v");
                        break;
                    case 1:
                        LogUtils.d("Logging d");
                        break;
                    case 2:
                        LogUtils.i("Logging i");
                        break;
                    case 3:
                        LogUtils.w("Logging w");
                        break;
                    case 4:
                        LogUtils.e("Logging e");
                        break;
                    case 5:
                        LogUtils.a("Logging a");
                        break;
                    default:
                        // do nothing
                }
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.d(s);
            }
        });
    }
}
