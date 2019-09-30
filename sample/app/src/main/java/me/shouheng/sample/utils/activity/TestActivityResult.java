package me.shouheng.sample.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.io.Serializable;

import me.shouheng.sample.R;
import me.shouheng.utils.app.ActivityUtils;

/**
 * @author shouh
 * @version $Id: TestActivityResult, v 0.1 2018/11/22 12:51 shouh Exp$
 */
public class TestActivityResult extends AppCompatActivity {

    public static final String REQUEST_EXTRA_KEY_DATA = "__request_extra_key_data";

    public static final String RESULT_EXTRA_KEY_DATA = "__result_extra_key_data";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_result);

        Request request = (Request) getIntent().getSerializableExtra(REQUEST_EXTRA_KEY_DATA);
        TextView tvRequest = findViewById(R.id.tv_request);
        if (request != null) {
            tvRequest.setText(request.toString());
        }

        Intent intent = new Intent();
        intent.putExtra(RESULT_EXTRA_KEY_DATA, new Result("Result-name", "Result-value"));
        setResult(Activity.RESULT_OK, intent);

        View btnFinish = findViewById(R.id.btn_finish);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            btnFinish.setTransitionName("SHARED_ELEMENT");
        }
        btnFinish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.finishActivity(TestActivityResult.this);
            }
        });
    }

    public static class Request implements Serializable {
        final String name;
        final String value;

        Request(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return "Request: " +
                    "name='" + name + '\'' +
                    ", value='" + value;
        }
    }

    public static class Result implements Serializable {
        final String name;
        final String value;

        Result(String name, String value) {
            this.name = name;
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return "Result: " +
                    "name='" + name + '\'' +
                    ", value='" + value;
        }
    }
}
