package me.shouheng.home.widget

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import me.shouheng.home.R
import me.shouheng.uix.common.anno.BottomButtonStyle
import me.shouheng.uix.common.bean.textStyle
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.content.simpleContent
import me.shouheng.uix.widget.dialog.footer.SimpleFooter
import me.shouheng.uix.widget.dialog.footer.simpleFooter
import me.shouheng.uix.widget.dialog.title.IDialogTitle
import me.shouheng.uix.widget.dialog.title.simpleTitle
import me.shouheng.utils.ktx.attrColorOf
import me.shouheng.utils.ktx.stringOf

/** Method extension for [EditText]. */
inline fun EditText.afterTextChanged(
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

/** Suggest to wrap your own logic to global methods. */
fun confirmOrCancel(
    context: Context,
    onConfirm: (content: IDialogContent) -> Unit
): SimpleFooter {
    return simpleFooter {
        withStyle(BottomButtonStyle.BUTTON_STYLE_DOUBLE)
        withLeft(stringOf(R.string.main_dialog_input_cancel))
        withLeftStyle(textStyle {
            withSize(16f)
            withColor(Color.GRAY)
        })
        withRight(stringOf(R.string.main_dialog_input_confirm))
        withRightStyle(textStyle {
            withSize(16f)
            withColor(context.attrColorOf(R.attr.colorAccent))
        })
        onLeft { dlg, _, _ -> dlg.dismiss() }
        onRight { dlg, _, content ->
            onConfirm.invoke(content!!)
            dlg.dismiss()
        }
    }
}

/** Simple dialog content. */
fun simpleDialogContent(
    content: String
): IDialogContent {
    return simpleContent {
        withContent(content)
        withStyle(textStyle {
            withSize(16f)
        })
    }
}

/** Simple dialog title. */
fun simpleDialogTitle(
    context: Context,
    text: String
): IDialogTitle {
    return simpleTitle {
        withTitle(text)
        withStyle(textStyle {
            withSize(18f)
            withColor(context.attrColorOf(R.attr.colorAccent))
        })
    }
}
