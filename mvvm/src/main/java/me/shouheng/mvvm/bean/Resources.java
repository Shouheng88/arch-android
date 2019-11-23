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
     * Field for appendix for current resource.
     */
    public final long udf1;

    /**
     * Field for appendix for current resource.
     */
    public final double udf2;

    /**
     * Field for appendix for current resource.
     */
    public final String udf3;

    /**
     * Field for appendix for current resource.
     */
    public final Object udf4;

    private Resources(Status status, T data, String code, String message,
                      long udf1, double udf2, String udf3, Object udf4) {
        this.status = status;
        this.data = data;
        this.code = code;
        this.message = message;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
        this.udf4 = udf4;
    }

    public static <U> Resources<U> success(U data) {
        return new Resources<>(Status.SUCCESS, data, null, null, 0, 0, null, null);
    }

    public static <U> Resources<U> success(U data, long udf1) {
        return new Resources<>(Status.SUCCESS, data, null, null, udf1, 0, null, null);
    }

    public static <U> Resources<U> success(U data, double udf2) {
        return new Resources<>(Status.SUCCESS, data, null, null, 0, udf2, null, null);
    }

    public static <U> Resources<U> success(U data, String udf3) {
        return new Resources<>(Status.SUCCESS, data, null, null, 0, 0, udf3, null);
    }

    public static <U> Resources<U> success(U data, Object udf4) {
        return new Resources<>(Status.SUCCESS, data, null, null, 0, 0, null, udf4);
    }

    public static <U> Resources<U> success(U data, long udf1, double udf2, String udf3, Object udf4) {
        return new Resources<>(Status.SUCCESS, data, null, null, udf1, udf2, udf3, udf4);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, 0, 0, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, long udf1) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, udf1, 0, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, double udf2) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, 0, udf2, null, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, String udf3) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, 0, 0, udf3, null);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, Object udf4) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, 0, 0, null, udf4);
    }

    public static <U> Resources<U> failed(String errorCode, String errorMessage, long udf1, double udf2, String udf3, Object udf4) {
        return new Resources<>(Status.FAILED, null, errorCode, errorMessage, udf1, udf2, udf3, udf4);
    }

    public static <U> Resources<U> loading() {
        return new Resources<>(Status.LOADING, null, null, null, 0, 0, null, null);
    }

    public static <U> Resources<U> loading(long udf1) {
        return new Resources<>(Status.LOADING, null, null, null, udf1, 0, null, null);
    }

    public static <U> Resources<U> loading(double udf2) {
        return new Resources<>(Status.LOADING, null, null, null, 0, udf2, null, null);
    }

    public static <U> Resources<U> loading(String udf3) {
        return new Resources<>(Status.LOADING, null, null, null, 0, 0, udf3, null);
    }

    public static <U> Resources<U> loading(Object udf4) {
        return new Resources<>(Status.LOADING, null, null, null, 0, 0, null, udf4);
    }

    public static <U> Resources<U> loading(long udf1, double udf2, String udf3, Object udf4) {
        return new Resources<>(Status.LOADING, null, null, null, udf1, udf2, udf3, udf4);
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
                '}';
    }
}
