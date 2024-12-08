package models;

/**
 * Cette classe représente un impact routier.
 */
public class RoadImpact {
    private String requestId;
    private String streetId;
    private String streetName;
    private String impactType;

    /**
     * Constructeur par défaut de la classe RoadImpact.
     */
    public RoadImpact() {}

    /**
     * Constructeur de la classe RoadImpact.
     *
     * @param requestId L'identifiant de la demande de travaux associée.
     * @param streetId L'identifiant de la rue.
     * @param streetName Le nom de la rue.
     * @param impactType Le type d'impact routier.
     */
    public RoadImpact(String requestId, String streetId, String streetName, String impactType) {
        this.requestId = requestId;
        this.streetId = streetId;
        this.streetName = streetName;
        this.impactType = impactType;
    }

    /**
     * Retourne l'identifiant de la demande de travaux associée.
     *
     * @return L'identifiant de la demande de travaux associée.
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Définit l'identifiant de la demande de travaux associée.
     *
     * @param requestId L'identifiant de la demande de travaux associée.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * Retourne l'identifiant de la rue.
     *
     * @return L'identifiant de la rue.
     */
    public String getStreetId() {
        return streetId;
    }

    /**
     * Définit l'identifiant de la rue.
     *
     * @param streetId L'identifiant de la rue.
     */
    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    /**
     * Retourne le nom de la rue.
     *
     * @return Le nom de la rue.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Définit le nom de la rue.
     *
     * @param streetName Le nom de la rue.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Retourne le type d'impact routier.
     *
     * @return Le type d'impact routier.
     */
    public String getImpactType() {
        return impactType;
    }

    /**
     * Définit le type d'impact routier.
     *
     * @param impactType Le type d'impact routier.
     */
    public void setImpactType(String impactType) {
        this.impactType = impactType;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'impact routier.
     *
     * @return Une chaîne de caractères représentant l'impact routier.
     */
    @Override
    public String toString() {
        return String.format("Rue: %s\nType d'impact: %s", 
            streetName, impactType);
    }
}