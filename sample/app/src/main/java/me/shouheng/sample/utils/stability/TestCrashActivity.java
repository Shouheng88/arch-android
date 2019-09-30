package me.shouheng.sample.utils.stability;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import me.shouheng.sample.R;
import me.shouheng.utils.stability.CrashHelper;

public class TestCrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_crash);

        final TextView tvList = findViewById(R.id.tv_list);
        findViewById(R.id.btn_list_logs).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<File> files = CrashHelper.listCrashFiles();
                StringBuilder sb = new StringBuilder();
                for (File file : files) {
                    sb.append(file.getAbsoluteFile()).append("\n");
                }
                tvList.setText(sb.toString());
            }
        });
        findViewById(R.id.btn_force_crash).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new IllegalStateException("Throw one exception to test the crash collector.");
            }
        });
    }
}
