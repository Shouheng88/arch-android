package me.shouheng.guokr.widget

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.guokr.api.bean.Result
import me.shouheng.guokr.R

/** Load guokr cover. */
fun BaseViewHolder.loadGuokrCover(item: Result) {
    Glide.with(this.itemView.context)
        .load(item.small_image)
        .placeholder(R.drawable.guokr_icon)
        .centerCrop()
        .into(getView<View>(R.id.iv_cover) as ImageView)
}

/** Load guokr author avatar. */
fun BaseViewHolder.loadGuokrAuthor(item: Result) {
    Glide.with(this.itemView.context)
        .load(item.author?.avatar?.normal)
        .placeholder(R.drawable.guokr_icon)
        .centerCrop()
        .into(getView<View>(R.id.civ_author) as ImageView)
}
