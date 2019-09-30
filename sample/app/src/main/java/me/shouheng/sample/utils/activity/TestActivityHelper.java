package me.shouheng.sample.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import me.shouheng.sample.R;
import me.shouheng.utils.app.ActivityUtils;
import me.shouheng.utils.stability.LogUtils;
import me.shouheng.utils.ui.ToastUtils;

/**
 * @author shouh
 * @version $Id: TestActivityHelper, v 0.1 2018/11/22 12:40 shouh Exp$
 */
public class TestActivityHelper extends AppCompatActivity {

    private static final int REQUEST_RESULT = 1;

    private static final int[] DIRECTION_ANIMATION_ARRAY = new int[]{
            ActivityUtils.ANIMATE_NONE,
            ActivityUtils.ANIMATE_FORWARD,
            ActivityUtils.ANIMATE_EASE_IN_OUT,
            ActivityUtils.ANIMATE_SLIDE_TOP_FROM_BOTTOM,
            ActivityUtils.ANIMATE_SLIDE_BOTTOM_FROM_TOP,
            ActivityUtils.ANIMATE_SCALE_IN,
            ActivityUtils.ANIMATE_SCALE_OUT
    };

    private static final String[] DIRECTION_ANIMATION_NAME_ARRAY = new String[]{
            "ANIMATE_NONE",
            "ANIMATE_FORWARD",
            "ANIMATE_EASE_IN_OUT",
            "ANIMATE_SLIDE_TOP_FROM_BOTTOM",
            "ANIMATE_SLIDE_BOTTOM_FROM_TOP",
            "ANIMATE_SCALE_IN",
            "ANIMATE_SCALE_OUT"
    };

    private int currentAnimationIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_test);

        findViewById(R.id.btn_view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.open()
                        .setAction(Intent.ACTION_VIEW)
                        .setData(Uri.parse("http://www.baidu.com"))
                        .launch(TestActivityHelper.this);
            }
        });
        findViewById(R.id.btn_request_code).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.open(TestActivityResult.class)
                        .put(TestActivityResult.REQUEST_EXTRA_KEY_DATA,
                                new TestActivityResult.Request("Request-name", "Request-value"))
                        .launch(TestActivityHelper.this, REQUEST_RESULT);
            }
        });

        final View btnShared = findViewById(R.id.btn_shared_activity);
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            btnShared.setTransitionName("SHARED_ELEMENT");
        }
        btnShared.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.open(TestActivityResult.class)
                        .wishSharedElements(new View[]{btnShared})
                        .launch(TestActivityHelper.this);
            }
        });

        findViewById(R.id.btn_animation).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = (currentAnimationIndex++)%DIRECTION_ANIMATION_ARRAY.length;
                ToastUtils.showShort(DIRECTION_ANIMATION_NAME_ARRAY[index]);
                ActivityUtils.start(TestActivityHelper.this,
                        TestActivityResult.class, 0,
                        DIRECTION_ANIMATION_ARRAY[index]);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.d("START HELPER");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("RESUME HELPER");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RESULT && resultCode == Activity.RESULT_OK) {
            assert data != null;
            TestActivityResult.Result result = (TestActivityResult.Result)
                    data.getSerializableExtra(TestActivityResult.RESULT_EXTRA_KEY_DATA);
            Toast.makeText(TestActivityHelper.this, result.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
