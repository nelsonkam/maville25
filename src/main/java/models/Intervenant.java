package models;

/**
 * Cette classe représente un intervenant, qui est un type d'utilisateur.
 */
public class Intervenant extends User {
    private String password;
    private String type;
    private String cityIdentifier;

    /**
     * Constructeur par défaut de la classe Intervenant.
     */
    public Intervenant() {}

    /**
     * Constructeur de la classe Intervenant.
     *
     * @param name Le nom de l'intervenant.
     * @param email L'email de l'intervenant.
     * @param password Le mot de passe de l'intervenant.
     * @param type Le type d'intervenant.
     * @param cityIdentifier L'identifiant de la ville de l'intervenant.
     */
    public Intervenant(String name, String email, String password, String type, String cityIdentifier) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.cityIdentifier = cityIdentifier;
    }

    /**
     * Retourne le type d'intervenant.
     *
     * @return Le type d'intervenant.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type d'intervenant.
     *
     * @param type Le type d'intervenant.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retourne l'identifiant de la ville de l'intervenant.
     *
     * @return L'identifiant de la ville de l'intervenant.
     */
    public String getCityIdentifier() {
        return cityIdentifier;
    }

    /**
     * Définit l'identifiant de la ville de l'intervenant.
     *
     * @param cityIdentifier L'identifiant de la ville de l'intervenant.
     */
    public void setCityIdentifier(String cityIdentifier) {
        this.cityIdentifier = cityIdentifier;
    }

    /**
     * Retourne le mot de passe de l'intervenant.
     *
     * @return Le mot de passe de l'intervenant.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Définit le mot de passe de l'intervenant.
     *
     * @param password Le mot de passe de l'intervenant.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Vérifie si le mot de passe fourni correspond au mot de passe de l'intervenant.
     *
     * @param password Le mot de passe à vérifier.
     * @return true si le mot de passe correspond, false sinon.
     */
    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}