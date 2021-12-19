package me.shouheng.sample.widget

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import me.shouheng.api.bean.Result
import me.shouheng.sample.R

/** Method extension for [EditText]. */
inline fun EditText.onTextChanged(
    crossinline onChanged: (s: Editable?) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            onChanged(s)
        }
    })
}

/** Load guokr cover. */
fun BaseViewHolder.loadGuokrCover(item: Result) {
    Glide.with(this.itemView.context)
        .load(item.small_image)
        .placeholder(R.drawable.main_guokr)
        .centerCrop()
        .into(getView<View>(R.id.iv_cover) as ImageView)
}

/** Load guokr author avatar. */
fun BaseViewHolder.loadGuokrAuthor(item: Result) {
    Glide.with(this.itemView.context)
        .load(item.author?.avatar?.normal)
        .placeholder(R.drawable.main_guokr)
        .centerCrop()
        .into(getView<View>(R.id.civ_author) as ImageView)
}
