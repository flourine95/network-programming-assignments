package exam.year23_24_code07.ex2;

public class Response {
    public enum Status {
        SUCCESS,
        ERROR
    }

    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_GREEN = "\u001B[32m"; // SUCCESS
    private static final String COLOR_RED = "\u001B[31m";   // ERROR

    private Status status;
    private String message;

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static String success(String message) {
        return new Response(Status.SUCCESS, COLOR_GREEN + message + COLOR_RESET).toString();
    }

    public static String error(String message) {
        return new Response(Status.ERROR, COLOR_RED + message + COLOR_RESET).toString();
    }

    @Override
    public String toString() {
        return "[" + status + "] " + message;
    }
}

