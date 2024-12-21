package models;

/**
 * Cette classe représente une candidature pour une demande de travaux.
 */
public class Candidature {
    private Long id;
    private Long workRequestId;
    private String intervenantEmail;
    private CandidatureStatus status;
    private String residentMessage; // Message optionnel du résident lorsqu'il sélectionne une candidature
    private boolean confirmedByIntervenant; // true une fois que l'intervenant a confirmé la candidature

    /**
     * Constructeur par défaut de la classe Candidature.
     */
    public Candidature() {}

    /**
     * Constructeur de la classe Candidature.
     *
     * @param workRequestId L'identifiant de la demande de travaux associée.
     * @param intervenantEmail L'email de l'intervenant.
     */
    public Candidature(Long workRequestId, String intervenantEmail) {
        this.workRequestId = workRequestId;
        this.intervenantEmail = intervenantEmail;
        this.status = CandidatureStatus.SUBMITTED;
        this.confirmedByIntervenant = false;
    }

    /**
     * Retourne l'identifiant de la candidature.
     *
     * @return L'identifiant de la candidature.
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de la candidature.
     *
     * @param id L'identifiant de la candidature.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne l'identifiant de la demande de travaux associée.
     *
     * @return L'identifiant de la demande de travaux associée.
     */
    public Long getWorkRequestId() {
        return workRequestId;
    }

    /**
     * Définit l'identifiant de la demande de travaux associée.
     *
     * @param workRequestId L'identifiant de la demande de travaux associée.
     */
    public void setWorkRequestId(Long workRequestId) {
        this.workRequestId = workRequestId;
    }

    /**
     * Retourne l'email de l'intervenant.
     *
     * @return L'email de l'intervenant.
     */
    public String getIntervenantEmail() {
        return intervenantEmail;
    }

    /**
     * Définit l'email de l'intervenant.
     *
     * @param intervenantEmail L'email de l'intervenant.
     */
    public void setIntervenantEmail(String intervenantEmail) {
        this.intervenantEmail = intervenantEmail;
    }

    /**
     * Retourne le statut de la candidature.
     *
     * @return Le statut de la candidature.
     */
    public CandidatureStatus getStatus() {
        return status;
    }

    /**
     * Définit le statut de la candidature.
     *
     * @param status Le statut de la candidature.
     */
    public void setStatus(CandidatureStatus status) {
        this.status = status;
    }

    /**
     * Retourne le message du résident.
     *
     * @return Le message du résident.
     */
    public String getResidentMessage() {
        return residentMessage;
    }

    /**
     * Définit le message du résident.
     *
     * @param residentMessage Le message du résident.
     */
    public void setResidentMessage(String residentMessage) {
        this.residentMessage = residentMessage;
    }

    /**
     * Indique si la candidature est confirmée par l'intervenant.
     *
     * @return true si la candidature est confirmée par l'intervenant, false sinon.
     */
    public boolean isConfirmedByIntervenant() {
        return confirmedByIntervenant;
    }

    /**
     * Définit si la candidature est confirmée par l'intervenant.
     *
     * @param confirmedByIntervenant true si la candidature est confirmée par l'intervenant, false sinon.
     */
    public void setConfirmedByIntervenant(boolean confirmedByIntervenant) {
        this.confirmedByIntervenant = confirmedByIntervenant;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la candidature.
     *
     * @return Une chaîne de caractères représentant la candidature.
     */
    @Override
    public String toString() {
        return String.format("Candidature #%d\nIntervenant: %s\nStatut: %s\nMessage Résident: %s\nConfirmée par Intervenant: %s",
        id, intervenantEmail, status, residentMessage == null ? "Aucun" : residentMessage, confirmedByIntervenant ? "Oui" : "Non" + "\n");
    }
}