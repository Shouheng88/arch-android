package me.shouheng.sample.widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/** Method extension for [EditText]. */
fun EditText.onTextChanged(
    onChanged: (s: Editable?) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            onChanged(s)
        }
    })
}
