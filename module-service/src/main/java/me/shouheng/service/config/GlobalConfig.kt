package me.shouheng.service.config

import me.shouheng.vmlib.bean.Resources
import me.shouheng.vmlib.bean.Status

/** Is succeed */
fun <T> Resources<T>.isSucceed() = this.status == Status.SUCCESS

