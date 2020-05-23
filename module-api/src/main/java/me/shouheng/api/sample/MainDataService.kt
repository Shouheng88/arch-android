package me.shouheng.api.sample

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Sample 模块数据加载服务接口
 *
 * @author WngShhng (shouheng2015@gmail.com)
 * @version 2019/7/5 23:32
 */
interface MainDataService : IProvider {

    /**
     * 加载主页的数据
     *
     * @param onGetMainDataListener 获取到主页数据时的回调接口
     */
    fun loadData(onGetMainDataListener: OnGetMainDataListener)
}

/**
 * 主页数据加载服务回调
 */
interface OnGetMainDataListener {

    /**
     * 当获取到数据的时候回调的方法
     */
    fun onGetData()
}