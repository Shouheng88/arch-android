package me.shouheng.mvvm.bean;

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
     * The code, read only.
     */
    public final String code;

    /**
     * The message of current resources, read only.
     */
    public final String message;

    /**
     * Appendix filed
     */
    public final Long udf1;

    /**
     * Appendix filed
     */
    public final Double udf2;

    /**
     * Appendix filed
     */
    public final Boolean udf3;

    /**
     * Appendix filed
     */
    public final String udf4;

    /**
     * Appendix filed
     */
    public final Object udf5;

    private Resources(Status status,
                      T data,
                      String code,
                      String message,
                      Long udf1,
                      Double udf2,
                      Boolean udf3,
                      String udf4,
                      Object udf5) {
        this.status = status;
        this.data = data;
        this.code = code;
        this.message = message;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
        this.udf5 = udf5;
    }

    public static <U> Resources<U> success(U data) {
        return new Resources<>(Status.SUCCESS, data, null, null, null, null, false, null, null);
    }

    public static <U> Resources<U> success(U data, long udf1) {
        return new Resources<>(Status.SUCCESS, data, null, null, udf1, null, false, null, null);
    }

    public static <U> Resources<U> success(U data, double udf2) {
        return new Resources<>(Status.SUCCESS, data, null, null, null, udf2, false, null, null);
    }

    public static <U> Resources<U> success(U data, boolean udf3) {
        return new Resources<>(Status.SUCCESS, data, null, null, null, null, udf3, null, null);
    }

    public static <U> Resources<U> success(U data, String udf4) {
        return new Resources<>(Status.SUCCESS, data, null, null, null, null, null, udf4, null);
    }

    public static <U> Resources<U> success(U data, Object udf5) {
        return new Resources<>(Status.SUCCESS, data, null, null, null, null, null, null, udf5);
    }

    public static <U> Resources<U> success(U data, Long udf1, Double udf2, Boolean udf3, String udf4, Object udf5) {
        return new Resources<>(Status.SUCCESS, data, null, null, udf1, udf2, udf3, udf4, udf5);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, null, null, null, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, long udf1) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, udf1, null, null, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, double udf2) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, null, udf2, null, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, boolean udf3) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, null, null, udf3, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, String udf4) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, null, null, null, udf4, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, Object udf5) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, null, null, null, null, udf5);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, Long udf1, Double udf2, Boolean udf3, String udf4, Object udf5) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, udf1, udf2, udf3, udf4, udf5);
    }

    public static <U> Resources<U> loading() {
        return new Resources<>(Status.LOADING, null, null, null, null, null, null, null, null);
    }

    public static <U> Resources<U> loading(long udf1) {
        return new Resources<>(Status.LOADING, null, null, null, udf1, null, null, null, null);
    }

    public static <U> Resources<U> loading(double udf2) {
        return new Resources<>(Status.LOADING, null, null, null, null, udf2, null, null, null);
    }

    public static <U> Resources<U> loading(boolean udf3) {
        return new Resources<>(Status.LOADING, null, null, null, null, null, udf3, null, null);
    }

    public static <U> Resources<U> loading(String udf4) {
        return new Resources<>(Status.LOADING, null, null, null, null, null, null, udf4, null);
    }

    public static <U> Resources<U> loading(Object udf5) {
        return new Resources<>(Status.LOADING, null, null, null, null, null, null, null, udf5);
    }

    public static <U> Resources<U> loading(long udf1, double udf2, boolean udf3, String udf4, Object udf5) {
        return new Resources<>(Status.LOADING, null, null, null, udf1, udf2, udf3, udf4, udf5);
    }

    @Override
    public String toString() {
        return "Resources{" +
                "status=" + status +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", udf1=" + udf1 +
                ", udf2=" + udf2 +
                ", udf3='" + udf3 + '\'' +
                ", udf4=" + udf4 +
                ", udf5=" + udf5 +
                '}';
    }
}
