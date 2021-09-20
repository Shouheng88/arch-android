package me.shouheng.sample.dialog

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.sample.R
import me.shouheng.sample.databinding.ActivityTipsBinding
import me.shouheng.uix.common.anno.LoadingStyle
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.common.utils.UView
import me.shouheng.uix.widget.dialog.MessageDialog
import me.shouheng.utils.constant.ActivityDirection
import me.shouheng.utils.ui.ImageUtils
import me.shouheng.vmlib.anno.ActivityConfiguration
import me.shouheng.vmlib.base.CommonActivity
import me.shouheng.vmlib.comn.EmptyViewModel

/**
 * Tips 示例
 *
 * @author <a href="mailto:shouheng2020@gmail.com">ShouhengWang</a>
 * @version 2020-04-18 22:45
 */
@Route(path = "/app/tip")
@ActivityConfiguration(exitDirection = ActivityDirection.ANIMATE_SLIDE_BOTTOM_FROM_TOP)
class TipsActivity : CommonActivity<EmptyViewModel, ActivityTipsBinding>() {

    override fun getLayoutResId(): Int = R.layout.activity_tips

    override fun doCreateView(savedInstanceState: Bundle?) {
        binding.btnLoading.setOnClickListener {
            val dlg = MessageDialog.showLoading(
                    this,
                    "加载中...\n[2秒之后自动关闭]",
                    false)
            dlg.show()
            Handler().postDelayed({ MessageDialog.hide(dlg) }, 2000)
        }
        binding.btnLoadingCustom.setOnClickListener {
            val dlg = MessageDialog.builder(
                    "抱歉，出错了!\n[2秒之后自动关闭]",
                    cancelable = false,
                    loading = false,
                    icon = ImageUtils.tintDrawable(R.drawable.uix_error_outline_black_24dp, Color.WHITE)
            ).withMessageStyle(TextStyleBean().apply {
                textColor = Color.WHITE
                typeFace = Typeface.BOLD
            }).withBorderRadius(UView.dp2px(20f)).build(context)
            dlg.show()
            Handler().postDelayed({ MessageDialog.hide(dlg) }, 2000)
        }
        binding.btnLoadingCancelable.setOnClickListener {
            MessageDialog.showLoading(this, "加载中...", true).show()
        }
        binding.btnLoadingCancelableLong.setOnClickListener {
            MessageDialog.builder(
                    "君不見黃河之水天上來，奔流到海不復回。 君不見高堂明鏡悲白髮，朝如青絲暮成雪。 人生得意須盡歡，莫使金樽空對月。 天生我材必有用，千金散盡還復來。 烹羊宰牛且爲樂，會須一飲三百杯。 岑夫子，丹丘生。將進酒，杯莫停。 與君歌一曲，請君爲我側耳聽。 鐘鼓饌玉不足貴，但願長醉不願醒。 古來聖賢皆寂寞，惟有飲者留其名。 陳王昔時宴平樂，斗酒十千恣讙謔。 主人何為言少錢？徑須沽取對君酌。 五花馬，千金裘。呼兒將出換美酒，與爾同銷萬古愁。",
                    true,
                    loadingStyle = LoadingStyle.STYLE_ANDROID
            ).withMessageStyle(TextStyleBean().apply {
                textSize = 14f
            }).build(context).show()
        }
    }
}