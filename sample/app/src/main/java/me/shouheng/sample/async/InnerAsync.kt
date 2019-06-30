package me.shouheng.sample.async

import android.os.AsyncTask

class InnerAsync(private val onFinish : OnFinish) : AsyncTask<Any, Any, Any>() {

    interface OnFinish {
        fun onFinish()
    }

    override fun doInBackground(vararg p0: Any?): Any {
        Thread.sleep(1000)
        return Object()
    }

    override fun onPostExecute(result: Any?) {
        onFinish.onFinish()
    }
}
