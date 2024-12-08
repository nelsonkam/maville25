package models;

/**
 * Cette classe abstraite représente un utilisateur.
 */
public abstract class User {
    protected String name;
    protected String email;

    /**
     * Retourne l'email de l'utilisateur.
     *
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le nom de l'utilisateur.
     *
     * @return Le nom de l'utilisateur.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param name Le nom de l'utilisateur.
     */
    public void setName(String name) {
        this.name = name;
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
     * Vérifie si le mot de passe fourni correspond au mot de passe de l'utilisateur.
     * Cette méthode doit être implémentée dans les classes filles.
     *
     * @param password Le mot de passe à vérifier.
     * @return true si le mot de passe correspond, false sinon.
     */
    public boolean checkPassword(String password) {
        // Cette méthode devrait être implémentée dans les classes filles
        return false;
    }
}