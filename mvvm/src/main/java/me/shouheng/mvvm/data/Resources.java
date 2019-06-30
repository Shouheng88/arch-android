package me.shouheng.mvvm.data;

/**
 * The wrapped resource class to transfer data between view model, view and model.
 *
 * @author WngShhng 2019-6-29
 * @param <T> the type of data.
 */
public class Resources<T> {

    /**
     * The status of current resources, read only.
     */
    public final Status status;

    /**
     * The data of current resources, read only.
     */
    public final T data;

    /**
     * The message of current reources, read only.
     */
    public final String message;

    private Object udf1;

    private Object udf2;

    private Object udf3;

    private Resources(Status status, T data, String message, Object udf1, Object udf2, Object udf3) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.udf1 = udf1;
        this.udf2 = udf2;
        this.udf3 = udf3;
    }

    public static <U> Resources<U> success(U data) {
        return new Resources<>(Status.SUCCESS, data, null, null, null, null);
    }

    public static <U> Resources<U> failed(String message) {
        return new Resources<>(Status.FAILED, null, message, null, null, null);
    }

    public static <U> Resources<U> failed(String message, U data) {
        return new Resources<>(Status.FAILED, data, message, null, null, null);
    }

    public static <U> Resources<U> loading() {
        return new Resources<>(Status.LOADING, null, null, null, null, null);
    }

    public static <U> Resources<U> loading(String message) {
        return new Resources<>(Status.LOADING, null, message, null, null, null);
    }

    public Object getUdf1() {
        return udf1;
    }

    public void setUdf1(Object udf1) {
        this.udf1 = udf1;
    }

    public Object getUdf2() {
        return udf2;
    }

    public void setUdf2(Object udf2) {
        this.udf2 = udf2;
    }

    public Object getUdf3() {
        return udf3;
    }

    public void setUdf3(Object udf3) {
        this.udf3 = udf3;
    }

    @Override
    public String toString() {
        return "Resources{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", udf1=" + udf1 +
                ", udf2=" + udf2 +
                ", udf3=" + udf3 +
                '}';
    }
}
