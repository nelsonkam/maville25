package cli;

public class LogoutException extends RuntimeException {
    public LogoutException() {
        super("User logged out");
    }
}
