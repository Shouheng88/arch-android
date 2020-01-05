package me.shouheng.api.impl

import android.content.Context
import android.os.AsyncTask
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.api.sample.MainDataService
import me.shouheng.api.sample.OnGetMainDataListener
import me.shouheng.utils.stability.L

/**
 * 主界面数据加载服务实现
 *
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/5 23:42
 */
@Route(path = "/api/data")
class MainDataServiceImpl : MainDataService {

    override fun loadData(onGetMainDataListener: OnGetMainDataListener) {
        InnerAsync(object : InnerAsync.OnFinish {
            override fun onFinish() {
                onGetMainDataListener.onGetData()
            }
        }).execute()
    }

    override fun init(context: Context?) {
        L.i(MainDataServiceImpl::class.java.name + " has init.")
    }
}

/**
 * 模拟异步任务
 *
 * @author WngShhng 2019-07-05
 */
class InnerAsync(private val onFinish : OnFinish) : AsyncTask<Any, Any, Any>() {

    interface OnFinish {
        fun onFinish()
    }

    override fun doInBackground(vararg p0: Any?): Any {
        Thread.sleep(2000)
        return Object()
    }

    override fun onPostExecute(result: Any?) {
        onFinish.onFinish()
    }
}