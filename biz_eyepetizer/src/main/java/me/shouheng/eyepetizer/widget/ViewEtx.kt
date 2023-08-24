package me.shouheng.eyepetizer.widget

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.uix.common.glide.CornersTransformation

/** Kotlin styled extension for [BaseViewHolder]. */
fun BaseViewHolder.loadCover(
    context: Context,
    @IdRes viewId: Int,
    url: String?,
    @DrawableRes thumbnail: Int
) {
    Glide.with(context)
        .load(url)
        .thumbnail(Glide.with(context).load(thumbnail))
        .into(getView<View>(viewId) as ImageView)
}

/** Kotlin styled extension for [BaseViewHolder]. */
fun BaseViewHolder.loadRoundImage(
    context: Context,
    @IdRes viewId: Int,
    url: String?,
    @DrawableRes thumbnail: Int,
    corner: Int
) {
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions().transforms(
                CenterCrop(),
                CornersTransformation(corner, 0, CornersTransformation.CornerType.ALL)
            ))
        .thumbnail(Glide.with(context).load(thumbnail))
        .into(getView<View>(viewId) as ImageView)
}
