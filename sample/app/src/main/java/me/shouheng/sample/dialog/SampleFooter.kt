package me.shouheng.sample.dialog

import android.content.Context
import android.view.View
import me.shouheng.sample.R
import me.shouheng.uix.widget.dialog.footer.IDialogFooter

/**
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019-10-13 17:51
 */
class SampleFooter : IDialogFooter {

    override fun getView(ctx: Context): View = View.inflate(ctx, R.layout.layout_dialog_bottom_sample, null)

}