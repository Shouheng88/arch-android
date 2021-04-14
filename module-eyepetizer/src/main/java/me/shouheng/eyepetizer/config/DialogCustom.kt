package me.shouheng.eyepetizer.config

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.StringRes
import android.view.Gravity
import me.shouheng.eyepetizer.R
import me.shouheng.uix.common.anno.*
import me.shouheng.uix.common.bean.TextStyleBean
import me.shouheng.uix.widget.dialog.BeautyDialog
import me.shouheng.uix.widget.dialog.MessageDialog
import me.shouheng.uix.widget.dialog.content.*
import me.shouheng.uix.widget.dialog.footer.SimpleFooter
import me.shouheng.uix.widget.dialog.title.IDialogTitle
import me.shouheng.uix.widget.dialog.title.SimpleTitle
import me.shouheng.uix.widget.rv.EmptyView
import me.shouheng.utils.app.ResUtils
import me.shouheng.utils.ktx.*
import me.shouheng.utils.ui.ImageUtils

fun clearDrawable(): Drawable = drawableOf(R.drawable.ic_baseline_cancel_24).tint(colorOf(R.color.text_color_secondary))

/** Get beauty dialog builder */
fun dialog(darkDialog: Boolean = false): BeautyDialog.Builder {
    return BeautyDialog.Builder().setDarkDialog(darkDialog)
}

/** Get custom title builder */
fun BeautyDialog.Builder.title(title: String, gravity: Int = Gravity.CENTER): BeautyDialog.Builder {
    this.setDialogTitle(
        SimpleTitle.Builder()
            .setTitle(title)
            .setTitleStyle(TextStyleBean().apply {
                this.gravity = gravity
                this.typeFace = Typeface.BOLD
            }).build())
    return this
}

/** Set the dialog launch from bottom */
fun BeautyDialog.Builder.bottom(
    fillParent: Boolean = false,
    darkDialog: Boolean = false
): BeautyDialog.Builder {
    setDialogPosition(DialogPosition.POS_BOTTOM)
    if (fillParent) {
        val dialogColor = if (darkDialog) BeautyDialog.GlobalConfig.darkBGColor else BeautyDialog.GlobalConfig.lightBGColor
        val radius = 8f.dp2px().toFloat()
        setDialogBackground(ImageUtils.getDrawable(dialogColor, radius, radius, 0f, 0f))
        setDialogStyle(DialogStyle.STYLE_MATCH)
        setDialogMargin(0)
    }
    return this
}

/** Set message content  */
fun BeautyDialog.Builder.content(message: String, gravity: Int = Gravity.CENTER): BeautyDialog.Builder {
    setDialogContent(
        SimpleContent.Builder()
            .setContent(message)
            .setGravity(gravity)
            .build()
    )
    return this
}

/** Empty view wrapper method */
fun CustomList.Builder.empty(ctx: Context): CustomList.Builder {
    setEmptyView(
        EmptyView.Builder(ctx)
            .setEmptyLoadingTips(stringOf(R.string.comn_load_loading))
            .setEmptyLoadingTipsColor(colorOf(R.color.text_color_secondary))
            .setLoadingStyle(LoadingStyle.STYLE_ANDROID)
            .setEmptyViewState(EmptyViewState.STATE_LOADING)
            .build())
    return this
}

/** Get custom editor builder */
fun BeautyDialog.Builder.editor(
    @ColorInt bottomLine: Int = Color.BLACK,
    hint: String = "",
    content: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 2,
    maxLength: Int = 100
): BeautyDialog.Builder {
    setDialogContent(
        SimpleEditor.Builder()
            .setSingleLine(singleLine)
            .setContent(content)
            .setHint(hint)
            .setMaxLines(maxLines)
            .setClearDrawable(clearDrawable())
            .setBottomLineColor(bottomLine)
            .setMaxLength(maxLength)
            .build())
    return this
}

/** Get custom list builder */
fun BeautyDialog.Builder.list(
    list: List<SimpleList.Item>,
    showIcon: Boolean = true,
    callback: (dlg: BeautyDialog, item: SimpleList.Item) -> Unit = {_, _ -> }
) : BeautyDialog.Builder {
    setDialogContent(
        SimpleList.Builder()
            .setList(list)
            .setShowIcon(showIcon)
            .setOnItemClickListener { dialog, item ->
                callback(dialog, item)
            }
            .build())
    return this
}

/** Get custom confirm and cancel builder */
fun BeautyDialog.Builder.confirmOrCancel(
    context: Context,
    confirm: String = stringOf(R.string.confirm),
    cancel: String = stringOf(R.string.cancel),
    onConfirm: (dlg: BeautyDialog, title: IDialogTitle?, content: IDialogContent?) -> Unit = { _, _, _ -> },
    onCancel: (dlg: BeautyDialog, title: IDialogTitle?, content: IDialogContent?) -> Unit = { dlg, _, _ -> dlg.dismiss() }
): BeautyDialog.Builder {
    setDialogBottom( SimpleFooter.Builder()
        .setLeftText(cancel)
        .setRightText(confirm)
        .setRightTextStyle(TextStyleBean().apply {
            textColor = ResUtils.getAttrColor(context, R.attr.colorAccent)
        })
        .setOnClickListener(object : SimpleFooter.OnClickListener {
            override fun onClick(dialog: BeautyDialog, buttonPos: Int, dialogTitle: IDialogTitle?, dialogContent: IDialogContent?) {
                if (buttonPos == BottomButtonPosition.BUTTON_POS_LEFT) {
                    onCancel(dialog, dialogTitle, dialogContent)
                } else {
                    onConfirm(dialog, dialogTitle, dialogContent)
                }
            }
        }).build())
    return this
}

/** Simple confirm dialog */
fun BeautyDialog.Builder.confirm(
    context: Context,
    confirm: String = stringOf(R.string.confirm),
    onConfirm: (dlg: BeautyDialog, title: IDialogTitle?, content: IDialogContent?) -> Unit = { dlg, _, _ -> dlg.dismiss() }
): BeautyDialog.Builder {
    setDialogBottom(
        SimpleFooter.Builder()
            .setBottomStyle(BottomButtonStyle.BUTTON_STYLE_SINGLE)
            .setMiddleText(confirm)
            .setMiddleTextStyle(TextStyleBean().apply {
                textColor = ResUtils.getAttrColor(context, R.attr.colorAccent)
            })
            .setOnClickListener(object : SimpleFooter.OnClickListener {
                override fun onClick(dialog: BeautyDialog, buttonPos: Int, dialogTitle: IDialogTitle?, dialogContent: IDialogContent?) {
                    if (buttonPos == BottomButtonPosition.BUTTON_POS_MIDDLE) {
                        onConfirm(dialog, dialogTitle, dialogContent)
                    }
                }
            }).build())
    return this
}

/** Dismiss dialog after do something */
fun BeautyDialog.dismiss(after: () -> Unit = {}) { after(); dismiss() }

/** Show one simple message by dialog */
fun message(
    context: Context,
    title: String? = null,
    message: String? = null,
    confirm: String = ResUtils.getString(R.string.confirm),
    cancel: String = ResUtils.getString(R.string.cancel),
    gravity: Int = Gravity.CENTER,
    onConfirm: (dialog: BeautyDialog) -> Unit = { dlg -> dlg.dismiss() },
    onCancel: (dialog: BeautyDialog) -> Unit = { dlg -> dlg.dismiss() },
    onDismiss:  (dialog: BeautyDialog) -> Unit = { dlg -> dlg.dismiss() }
): BeautyDialog {
    val builder = dialog().setDialogStyle(DialogStyle.STYLE_WRAP)
    if (title != null) builder.title(title, gravity)
    if (message != null) builder.content(message, gravity)
    return builder.confirmOrCancel(context, confirm, cancel,
        {dlg, _, _ -> onConfirm(dlg)},
        {dlg, _, _ -> onCancel(dlg)}).onDismiss { onDismiss(it) }.build()
}

/** Show loading dialog */
fun loading(context: Context, @StringRes msgResId: Int, cancelable: Boolean = true): Dialog =
    MessageDialog.showLoading(context, msgResId = msgResId, cancelable = cancelable,
        loadingStyle = LoadingStyle.STYLE_ANDROID).apply { this.show() }

/** Show loading dialog */
fun loading(context: Context, msg: String, cancelable: Boolean = false): Dialog =
    MessageDialog.showLoading(context, msg, cancelable,
        loadingStyle = LoadingStyle.STYLE_ANDROID).apply { this.show() }