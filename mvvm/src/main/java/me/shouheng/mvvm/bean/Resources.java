package me.shouheng.mvvm.bean;

import android.support.annotation.NonNull;

/**
 * The wrapped resource class to transfer data between view model, view and model.
 *
 * @author WngShhng 2019-6-29
 * @param <T> the type of data.
 */
public final class Resources<T> {

    /**
     * The status of current resources, read only.
     */
    public final Status status;

    /**
     * The data of current resources, read only.
     */
    public final T data;

    /**
     * The error code, read only.
     */
    public final String errorCode;

    /**
     * The message of current resources, read only.
     */
    public final String errorMessage;

    private Resources(Status status, T data, String errorCode, String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static <U> Resources<U> success(U data) {
        return new Resources<>(Status.SUCCESS, data, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage);
    }

    public static <U> Resources<U> loading() {
        return new Resources<>(Status.LOADING, null, null, null);
    }

    @NonNull
    @Override
    public String toString() {
        return "Resources{" +
                "status=" + status +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
