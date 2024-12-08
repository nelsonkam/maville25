package api.response;

import models.Intervenant;

/**
 * Cette classe représente la réponse contenant les informations d'un intervenant.
 */
public class IntervenantResponse {
    private String name;
    private String email;
    private String type;
    private String cityIdentifier;

    /**
     * Constructeur de la classe IntervenantResponse.
     * Initialise les champs à partir d'un objet Intervenant.
     *
     * @param intervenant L'intervenant dont les informations sont utilisées pour initialiser la réponse.
     */
    public IntervenantResponse(Intervenant intervenant) {
        this.name = intervenant.getName();
        this.email = intervenant.getEmail();
        this.type = intervenant.getType();
        this.cityIdentifier = intervenant.getCityIdentifier();
    }

    /**
     * Constructeur par défaut pour Jackson.
     */
    public IntervenantResponse() {}

    /**
     * Retourne le nom de l'intervenant.
     *
     * @return Le nom de l'intervenant.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'intervenant.
     *
     * @param name Le nom de l'intervenant.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne l'email de l'intervenant.
     *
     * @return L'email de l'intervenant.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email de l'intervenant.
     *
     * @param email L'email de l'intervenant.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le type de l'intervenant.
     *
     * @return Le type de l'intervenant.
     */
    public String getType() {
        return type;
    }

    /**
     * Définit le type de l'intervenant.
     *
     * @param type Le type de l'intervenant.
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
}