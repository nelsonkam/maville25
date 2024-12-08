package cli;

/**
 * Exception lancée lorsque l'utilisateur se déconnecte.
 */
public class LogoutException extends RuntimeException {

    /**
     * Constructeur de la classe LogoutException.
     * Initialise l'exception avec un message par défaut indiquant que l'utilisateur s'est déconnecté.
     */
    public LogoutException() {
        super("User logged out");
    }
}