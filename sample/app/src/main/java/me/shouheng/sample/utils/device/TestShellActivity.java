package me.shouheng.sample.utils.device;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import me.shouheng.sample.R;
import me.shouheng.sample.utils.common.BaseActivity;
import me.shouheng.utils.device.ShellUtils;

public class TestShellActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_shell);

        final TextView tv = findViewById(R.id.tv);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        final EditText et = findViewById(R.id.et);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ShellUtils.CommandResult result =
                            ShellUtils.execCmd(et.getText().toString(), false);
                    tv.setText(result.toString());
                }
                return true;
            }
        });
    }
}
