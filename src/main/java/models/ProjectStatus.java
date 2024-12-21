package models;

/**
 * Cette énumération représente les différents statuts qu'un projet peut avoir.
 */

public enum ProjectStatus {
    /**
     * Projet prévu.
     */
    PLANNED("Prévu"),

    /**
     * Projet en cours.
     */
    IN_PROGRESS("En cours"),

    /**
     * Projet terminé.
     */
    COMPLETED("Terminé"),

    /**
     * Projet annulé.
     */
    CANCELLED("Annulé");

    private final String displayName;

    /**
     * Constructeur de l'énumération ProjectStatus.
     *
     * @param displayName Le nom affiché du statut.
     */
    ProjectStatus(String displayName) {
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
