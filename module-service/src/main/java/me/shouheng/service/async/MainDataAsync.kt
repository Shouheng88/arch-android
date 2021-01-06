package me.shouheng.service.async

import android.os.AsyncTask
import me.shouheng.utils.ktx.nowString

/** Mock async */
class MainDataAsync(private val onTaskCompleted : OnTaskCompleted) : AsyncTask<Any, Any, String>() {

    interface OnTaskCompleted {
        fun onCompleted(data: String)
    }

    override fun doInBackground(vararg p0: Any?): String {
        Thread.sleep(3000)
        return "Task completed at ${nowString()}."
    }

    override fun onPostExecute(result: String) {
        onTaskCompleted.onCompleted(result)
    }
}