package models;

import java.time.LocalDate;

/**
 * Cette classe représente une demande de travaux.
 */
public class WorkRequest {
    private Long id;
    private String title;
    private String description;
    private String workType;
    private LocalDate desiredStartDate;
    private WorkRequestStatus status;
    private String residentEmail;

    /**
     * Constructeur par défaut de la classe WorkRequest.
     */
    public WorkRequest() {}

    /**
     * Constructeur de la classe WorkRequest.
     *
     * @param title Le titre de la demande de travaux.
     * @param description La description de la demande de travaux.
     * @param workType Le type de travaux.
     * @param desiredStartDate La date de début souhaitée pour les travaux.
     * @param residentEmail L'email du résident ayant fait la demande.
     */
    public WorkRequest(String title, String description, String workType,
                       LocalDate desiredStartDate, String residentEmail) {
        this.title = title;
        this.description = description;
        this.workType = workType;
        this.desiredStartDate = desiredStartDate;
        this.residentEmail = residentEmail;
        this.status = WorkRequestStatus.PENDING;
    }

    /**
     * Retourne l'identifiant de la demande de travaux.
     *
     * @return L'identifiant de la demande de travaux.
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la demande de travaux.
     *
     * @param id L'identifiant de la demande de travaux.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le titre de la demande de travaux.
     *
     * @return Le titre de la demande de travaux.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de la demande de travaux.
     *
     * @param title Le titre de la demande de travaux.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retourne la description de la demande de travaux.
     *
     * @return La description de la demande de travaux.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description de la demande de travaux.
     *
     * @param description La description de la demande de travaux.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retourne le type de travaux.
     *
     * @return Le type de travaux.
     */
    public String getWorkType() {
        return workType;
    }

    /**
     * Définit le type de travaux.
     *
     * @param workType Le type de travaux.
     */
    public void setWorkType(String workType) {
        this.workType = workType;
    }

    /**
     * Retourne la date de début souhaitée pour les travaux.
     *
     * @return La date de début souhaitée pour les travaux.
     */
    public LocalDate getDesiredStartDate() {
        return desiredStartDate;
    }

    /**
     * Définit la date de début souhaitée pour les travaux.
     *
     * @param desiredStartDate La date de début souhaitée pour les travaux.
     */
    public void setDesiredStartDate(LocalDate desiredStartDate) {
        this.desiredStartDate = desiredStartDate;
    }

    /**
     * Retourne le statut de la demande de travaux.
     *
     * @return Le statut de la demande de travaux.
     */
    public WorkRequestStatus getStatus() {
        return status;
    }

    /**
     * Définit le statut de la demande de travaux.
     *
     * @param status Le statut de la demande de travaux.
     */
    public void setStatus(WorkRequestStatus status) {
        this.status = status;
    }

    /**
     * Retourne l'email du résident ayant fait la demande.
     *
     * @return L'email du résident ayant fait la demande.
     */
    public String getResidentEmail() {
        return residentEmail;
    }

    /**
     * Définit l'email du résident ayant fait la demande.
     *
     * @param residentEmail L'email du résident ayant fait la demande.
     */
    public void setResidentEmail(String residentEmail) {
        this.residentEmail = residentEmail;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la demande de travaux.
     *
     * @return Une chaîne de caractères représentant la demande de travaux.
     */
    @Override
    public String toString() {
        return String.format("""
            Requête de travaux #%d
            Titre: %s
            Type: %s
            Date souhaitée: %s
            Statut: %s
            Description: %s""",
            id, title, workType, desiredStartDate, status, description);
    }
}