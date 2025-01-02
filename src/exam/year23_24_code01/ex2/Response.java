package exam.year23_24_code01.ex2;

public class Response<T> {
    public enum Status {
        OK, ERROR
    }

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_GREEN = "\u001B[32m"; // OK
    private static final String COLOR_RED = "\u001B[31m";   // ERROR
    private Status status;
    private T data;
    private String message;

    public Response(Status status, T data) {
        this(status, data, null);
    }

    public Response(Status status, String message) {
        this(status, null, message);
    }

    public Response(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    public static String ok(String message) {
        return new Response<>(Status.OK, message).toString();
    }

   public static String error(String message) {
        return new Response<>(Status.ERROR, message).toString();
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        String color = status == Status.OK ? COLOR_GREEN : COLOR_RED;
        return "[" + status + "] " + color + message + COLOR_RESET;
    }
}
