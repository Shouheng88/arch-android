package me.shouheng.sample.utils.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import me.shouheng.sample.R;
import me.shouheng.utils.ui.ImageUtils;
import me.shouheng.utils.ui.ViewUtils;

public class TestImageUtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_utils);

        Bitmap src = ImageUtils.getBitmap(R.drawable.img_lena);
        Bitmap watermark = ImageUtils.getBitmap(R.drawable.ic_widgets_black_24dp);
        int width = src.getWidth();
        int height = src.getHeight();

        LinearLayout ll = findViewById(R.id.ll);
        addImage(ll, src, "Original");
        addImage(ll, ImageUtils.drawColor(src, Color.parseColor("#8000FF00")), "Add Color");
        addImage(ll, ImageUtils.scale(src, width / 2, height / 2), "Scale");
        addImage(ll, ImageUtils.clip(src, 0, 0, width / 2, height / 2), "Clip");
        addImage(ll, ImageUtils.skew(src, 0.2f, 0.1f), "Skew");
        addImage(ll, ImageUtils.rotate(src, 90, width / 2, height / 2), "Rotate");
        addImage(ll, ImageUtils.toRound(src), "Round");
        addImage(ll, ImageUtils.toRound(src, 16, Color.GREEN), "Round");
        addImage(ll, ImageUtils.toRoundCorner(src, 80), "Round Corner");
        addImage(ll, ImageUtils.toRoundCorner(src, 80, 16, Color.GREEN), "Round Corner");
        addImage(ll, ImageUtils.addCornerBorder(src, 16, Color.GREEN, 0), "Corner Border");
        addImage(ll, ImageUtils.addTextWatermark(src, "Watermark", 40, Color.GREEN, 0, 0), "Text Watermark");
        addImage(ll, ImageUtils.addImageWatermark(src, watermark, 0, 0, 0x88), "Image Watermark");
        addImage(ll, ImageUtils.toGray(src), "Grey");
        addImage(ll, ImageUtils.fastBlur(src, 0.1f, 5), "Fast Blur");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            addImage(ll, ImageUtils.renderScriptBlur(src, 10), "Render Script Blur");
        }
        addImage(ll, ImageUtils.stackBlur(src, 10), "Stack Blur");
        addImage(ll, ImageUtils.compressByScale(src, 0.5f, 0.5f), "Compress By Scale");
        addImage(ll, ImageUtils.compressByQuality(src, 50), "Compress By Quality");
        addImage(ll, ImageUtils.compressByQuality(src, 10L * 1024), "Compress By Quality");// 10Kb
        addImage(ll, ImageUtils.compressBySampleSize(src, 2), "Compress By Sample Size");
        addImage(ll, ImageUtils.drawable2Bitmap(ImageUtils.tintDrawable(R.drawable.ic_widgets_black_24dp, Color.BLUE)), "Tint Drawable");
    }

    private void addImage(LinearLayout container, Bitmap bitmap, String title) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = ViewUtils.dp2px(5);
        params.topMargin = ViewUtils.dp2px(5);
        ll.setLayoutParams(params);
        ImageView iv = new ImageView(this);
        int dp200 = ViewUtils.dp2px(200);
        iv.setLayoutParams(new LinearLayout.LayoutParams(dp200, dp200));
        iv.setImageBitmap(bitmap);
        TextView tv = new TextView(this);
        tv.setText(title);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.gravity = Gravity.CENTER;
        tv.setLayoutParams(tvParams);
        ll.addView(iv);
        ll.addView(tv);
        container.addView(ll);
    }
}
