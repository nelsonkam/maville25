package api;

/**
 * Cette classe représente une requête de connexion avec un email et un mot de passe.
 */
public class LoginRequest {
    private String email;
    private String password;

    /**
     * Constructeur par défaut pour Jackson.
     */
    public LoginRequest() {}

    /**
     * Constructeur de la classe LoginRequest.
     *
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Retourne l'email de l'utilisateur.
     *
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'utilisateur.
     *
     * @param email L'email de l'utilisateur.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'utilisateur.
     *
     * @param password Le mot de passe de l'utilisateur.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}