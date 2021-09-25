package me.shouheng.vmlib.component

import me.shouheng.vmlib.bus.Bus

/** Post one event by EventBus */
fun post(event: Any) {
    Bus.get().post(event)
}

/**Post one sticky event by EventBus */
fun postSticky(event: Any) {
    Bus.get().postSticky(event)
}
