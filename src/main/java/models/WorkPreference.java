package models;

import java.time.LocalTime;

/**
 * Représente les préférences horaires d'un résident pour une requête de travaux
 * spécifique.
 */
public class WorkPreference {
    private Long id; // Identifiant unique
    private String residentEmail; // Email du résident
    private Long workRequestId; // ID de la requête de travaux
    private LocalTime preferredStartTime; // Heure de début préférée
    private LocalTime preferredEndTime; // Heure de fin préférée

    /**
     * Constructeur par défaut pour les frameworks (ex. : Jackson).
     */
    public WorkPreference() {
    }

    /**
     * Constructeur principal pour créer une préférence horaire.
     *
     * @param residentEmail      Email du résident associé.
     * @param workRequestId      Identifiant de la requête de travaux.
     * @param preferredStartTime Heure de début préférée.
     * @param preferredEndTime   Heure de fin préférée.
     */
    public WorkPreference(String residentEmail, Long workRequestId, LocalTime preferredStartTime,
            LocalTime preferredEndTime) {
        this.residentEmail = residentEmail;
        this.workRequestId = workRequestId;
        this.preferredStartTime = preferredStartTime;
        this.preferredEndTime = preferredEndTime;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResidentEmail() {
        return residentEmail;
    }

    public void setResidentEmail(String residentEmail) {
        this.residentEmail = residentEmail;
    }

    public Long getWorkRequestId() {
        return workRequestId;
    }

    public void setWorkRequestId(Long workRequestId) {
        this.workRequestId = workRequestId;
    }

    public LocalTime getPreferredStartTime() {
        return preferredStartTime;
    }

    public void setPreferredStartTime(LocalTime preferredStartTime) {
        this.preferredStartTime = preferredStartTime;
    }

    public LocalTime getPreferredEndTime() {
        return preferredEndTime;
    }

    public void setPreferredEndTime(LocalTime preferredEndTime) {
        this.preferredEndTime = preferredEndTime;
    }
}
