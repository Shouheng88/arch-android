package me.shouheng.api.sample

import com.alibaba.android.arouter.facade.template.IProvider

/** Service for main page data */
interface MainDataService : IProvider {

    /** Load main page data */
    fun loadData(onGetMainDataListener: OnGetMainDataListener)
}

interface OnGetMainDataListener {

    /** On get main page data */
    fun onGetData(data: String)
}