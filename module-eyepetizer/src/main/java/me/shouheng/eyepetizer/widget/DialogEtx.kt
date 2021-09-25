package me.shouheng.eyepetizer.widget

import android.content.Context
import android.graphics.Color
import me.shouheng.eyepetizer.R
import me.shouheng.uix.common.anno.BottomButtonStyle
import me.shouheng.uix.common.bean.textStyle
import me.shouheng.uix.widget.dialog.content.IDialogContent
import me.shouheng.uix.widget.dialog.footer.SimpleFooter
import me.shouheng.uix.widget.dialog.footer.simpleFooter
import me.shouheng.uix.widget.dialog.title.IDialogTitle
import me.shouheng.uix.widget.dialog.title.simpleTitle
import me.shouheng.utils.ktx.attrColorOf
import me.shouheng.utils.ktx.stringOf

/** Suggest to wrap your own logic to global methods. */
fun confirmOrCancel(
    context: Context,
    onConfirm: (content: IDialogContent) -> Unit
): SimpleFooter {
    return simpleFooter {
        withStyle(BottomButtonStyle.BUTTON_STYLE_DOUBLE)
        withLeft(stringOf(R.string.eye_dialog_input_cancel))
        withLeftStyle(textStyle {
            withSize(16f)
            withColor(Color.GRAY)
        })
        withRight(stringOf(R.string.eye_dialog_input_confirm))
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
