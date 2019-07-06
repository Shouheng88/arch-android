package me.shouheng.eyepetizer

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.api.bean.Item

/**
 * @author shouh
 * @version $Id: HomeAdapter, v 0.1 2018/8/19 18:19 shouh Exp$
 */
class HomeAdapter(private val context: Context) :
    BaseQuickAdapter<Item, BaseViewHolder>(R.layout.eyepetizer_tem_home, mutableListOf<Item>()) {

    override fun convert(helper: BaseViewHolder, item: Item) {
        helper.setText(R.id.tv_title, item.data.title)
        helper.setText(R.id.tv_sub_title, item.data.author?.name + " | " + item.data.category)
        item.data.cover
        Glide.with(context)
            .load(item.data.cover?.homepage)
            .thumbnail(Glide.with(context).load(R.drawable.recommend_summary_card_bg_unlike))
            .into(helper.getView<View>(R.id.iv_cover) as ImageView)
        item.data.author
        Glide.with(context)
            .load(item.data.author?.icon)
            .thumbnail(Glide.with(context).load(R.mipmap.eyepetizer))
            .into(helper.getView<View>(R.id.iv_author) as ImageView)
    }
}
