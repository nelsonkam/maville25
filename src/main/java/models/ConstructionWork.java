package models;

/**
 * Cette classe représente des travaux de construction.
 */
public class ConstructionWork {
    private String id;
    private String boroughId;
    private String currentStatus;
    private String reasonCategory;
    private String submitterCategory;
    private String organizationName;

    /**
     * Constructeur par défaut de la classe ConstructionWork.
     */
    public ConstructionWork() {}

    /**
     * Constructeur de la classe ConstructionWork.
     *
     * @param id L'identifiant des travaux.
     * @param boroughId L'identifiant de l'arrondissement.
     * @param currentStatus Le statut actuel des travaux.
     * @param reasonCategory La catégorie de la raison des travaux.
     * @param submitterCategory La catégorie du soumissionnaire.
     * @param organizationName Le nom de l'organisation responsable des travaux.
     */
    public ConstructionWork(String id, String boroughId, String currentStatus,
                            String reasonCategory, String submitterCategory, String organizationName) {
        this.id = id;
        this.boroughId = boroughId;
        this.currentStatus = currentStatus;
        this.reasonCategory = reasonCategory;
        this.submitterCategory = submitterCategory;
        this.organizationName = organizationName;
    }

    /**
     * Retourne l'identifiant des travaux.
     *
     * @return L'identifiant des travaux.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant des travaux.
     *
     * @param id L'identifiant des travaux.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retourne l'identifiant de l'arrondissement.
     *
     * @return L'identifiant de l'arrondissement.
     */
    public String getBoroughId() {
        return boroughId;
    }

    /**
     * Définit l'identifiant de l'arrondissement.
     *
     * @param boroughId L'identifiant de l'arrondissement.
     */
    public void setBoroughId(String boroughId) {
        this.boroughId = boroughId;
    }

    /**
     * Retourne le statut actuel des travaux.
     *
     * @return Le statut actuel des travaux.
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * Définit le statut actuel des travaux.
     *
     * @param currentStatus Le statut actuel des travaux.
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * Retourne la catégorie de la raison des travaux.
     *
     * @return La catégorie de la raison des travaux.
     */
    public String getReasonCategory() {
        return reasonCategory;
    }

    /**
     * Définit la catégorie de la raison des travaux.
     *
     * @param reasonCategory La catégorie de la raison des travaux.
     */
    public void setReasonCategory(String reasonCategory) {
        this.reasonCategory = reasonCategory;
    }

    /**
     * Retourne la catégorie du soumissionnaire.
     *
     * @return La catégorie du soumissionnaire.
     */
    public String getSubmitterCategory() {
        return submitterCategory;
    }

    /**
     * Définit la catégorie du soumissionnaire.
     *
     * @param submitterCategory La catégorie du soumissionnaire.
     */
    public void setSubmitterCategory(String submitterCategory) {
        this.submitterCategory = submitterCategory;
    }

    /**
     * Retourne le nom de l'organisation responsable des travaux.
     *
     * @return Le nom de l'organisation responsable des travaux.
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Définit le nom de l'organisation responsable des travaux.
     *
     * @param organizationName Le nom de l'organisation responsable des travaux.
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères des travaux de construction.
     *
     * @return Une chaîne de caractères représentant les travaux de construction.
     */
    @Override
    public String toString() {
        return String.format("Travaux #%s\nArrondissement: %s\nStatut: %s\nType: %s\nIntervenant: %s (%s)",
            id, boroughId, currentStatus, reasonCategory, organizationName, submitterCategory);
    }
}