package me.shouheng.vmlib.bean;

/**
 * The loading status used between view and view model.
 *
 * @author WngShhng 2019-6-29
 */
public enum Status {
    /** status success */
    SUCCESS(0),
    /** status failed */
    FAILED( 1),
    /** status loading */
    LOADING(2);

    public final int id;

    Status(int id) {
        this.id = id;
    }
}
