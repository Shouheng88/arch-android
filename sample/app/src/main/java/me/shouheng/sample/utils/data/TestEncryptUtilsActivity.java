package me.shouheng.sample.utils.data;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import me.shouheng.sample.R;
import me.shouheng.utils.data.EncryptUtils;

public class TestEncryptUtilsActivity extends AppCompatActivity {

    private EditText et;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_encrypt_utils);
        et = findViewById(R.id.et);
        tvResult = findViewById(R.id.tv_result);
    }

    public void doEncrypt(View view) {
        String s = et.getText().toString();
        String result = "MD2 : " + EncryptUtils.md2(s) + "\n"
                + "MD5 : " + EncryptUtils.md5(s) + "\n"
                + "SHA1 : " + EncryptUtils.sha1(s) + "\n"
                + "SHA224 : " + EncryptUtils.sha224(s) + "\n"
                + "SHA256 : " + EncryptUtils.sha256(s) + "\n"
                + "SHA384 : " + EncryptUtils.sha384(s) + "\n"
                + "SHA512 : " + EncryptUtils.sha512(s);
        tvResult.setText(result);
    }
}
