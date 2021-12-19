package me.shouheng.vmlib.bean;

/**
 * The loading status used between view and view model.
 *
 * @author ShouhengWang 2019-6-29
 */
public enum Status {
    /** Status success */
    SUCCESS( 0),
    /** Status failed */
    FAILED(  1),
    /** Status loading */
    LOADING( 2),
    /** Status progress changed */
    PROGRESS(3);

    public final int id;

    Status(int id) {
        this.id = id;
    }
}
