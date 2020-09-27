package me.shouheng.vmlib.tools;

import me.shouheng.vmlib.bean.Resources;

/**
 * The state observer for vm lib. Used to handle event of given state.
 *
 * @param <T> the data type
 * @see me.shouheng.vmlib.base.BaseActivity#observe(Class, StateObserver, StateObserver, StateObserver)
 * @see me.shouheng.vmlib.base.BaseFragment#observe(Class, StateObserver, StateObserver, StateObserver)
 */
public interface StateObserver<T> {

    /**
     * Method to handle state change.
     *
     * @param resources the result wrapper
     */
    void onChanged(Resources<T> resources);
}
