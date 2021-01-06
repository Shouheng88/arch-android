package me.shouheng.service.impl

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import me.shouheng.service.async.MainDataAsync
import me.shouheng.api.sample.MainDataService
import me.shouheng.api.sample.OnGetMainDataListener
import me.shouheng.utils.stability.L

/**
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/5 23:42
 */
@Route(path = "/api/data")
class MainDataServiceImpl : MainDataService {

    override fun loadData(onGetMainDataListener: OnGetMainDataListener) {
        MainDataAsync(object : MainDataAsync.OnTaskCompleted {
            override fun onCompleted(data: String) {
                onGetMainDataListener.onGetData(data)
            }
        }).execute()
    }

    override fun init(context: Context?) {
        L.i(MainDataServiceImpl::class.java.name + " initialized.")
    }
}
