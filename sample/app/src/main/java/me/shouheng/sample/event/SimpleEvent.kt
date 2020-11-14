package me.shouheng.sample.event

/**
 * 一个简单的，用来在本模块内部使用的消息数据模型
 *
 * @author WngShhng 2019-07-05
 */
data class SimpleEvent(val msg: String)

data class StartForResult(val sid: Int)
