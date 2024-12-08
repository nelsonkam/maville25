package models;

/**
 * Cette énumération représente les différents statuts qu'une demande de travaux peut avoir.
 */
public enum WorkRequestStatus {
    /**
     * Demande de travaux en attente.
     */
    PENDING("En attente"),

    /**
     * Demande de travaux assignée.
     */
    ASSIGNED("Assigné"),

    /**
     * Demande de travaux en cours.
     */
    IN_PROGRESS("En cours"),

    /**
     * Demande de travaux terminée.
     */
    COMPLETED("Terminé"),

    /**
     * Demande de travaux annulée.
     */
    CANCELLED("Annulé");

    private final String displayName;

    /**
     * Constructeur de l'énumération WorkRequestStatus.
     *
     * @param displayName Le nom affiché du statut.
     */
    WorkRequestStatus(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Retourne le nom affiché du statut.
     *
     * @return Le nom affiché du statut.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du statut.
     *
     * @return Une chaîne de caractères représentant le statut.
     */
    @Override
    public String toString() {
        return displayName;
    }
}