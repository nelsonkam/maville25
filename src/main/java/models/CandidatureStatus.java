package models;

/**
 * Cette énumération représente les différents statuts qu'une candidature peut avoir.
 */
public enum CandidatureStatus {
    /**
     * Candidature soumise.
     */
    SUBMITTED("Soumise"),

    /**
     * Candidature retirée.
     */
    WITHDRAWN("Retirée"),

    /**
     * Candidature sélectionnée par le résident.
     */
    SELECTED_BY_RESIDENT("Sélectionnée par le résident"),

    /**
     * Candidature confirmée par l'intervenant.
     */
    CONFIRMED_BY_INTERVENANT("Confirmée par l'intervenant");

    private final String displayName;

    /**
     * Constructeur de l'énumération CandidatureStatus.
     *
     * @param displayName Le nom affiché du statut.
     */
    CandidatureStatus(String displayName) {
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