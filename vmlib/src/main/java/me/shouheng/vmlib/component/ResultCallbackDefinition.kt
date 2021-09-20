package me.shouheng.vmlib.component

import android.content.Intent

/** The result request callback definition. */
data class ResultCallbackDefinition(

    /** The request code. */
    val requestCode: Int,

    /** Remove the result handler when called once. */
    val removeWhenCalled: Boolean,

    /** The handler to handle result. */
    val handler: (code: Int, data: Intent?) -> Unit
)
