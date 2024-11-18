package models;

import java.time.LocalDate;

public class WorkRequest {
    private Long id;
    private String title;
    private String description;
    private String workType;
    private LocalDate desiredStartDate;
    private WorkRequestStatus status;
    private String residentEmail;

    public WorkRequest() {}

    public WorkRequest(String title, String description, String workType,
                       LocalDate desiredStartDate, String residentEmail) {
        this.title = title;
        this.description = description;
        this.workType = workType;
        this.desiredStartDate = desiredStartDate;
        this.residentEmail = residentEmail;
        this.status = WorkRequestStatus.PENDING;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWorkType() { return workType; }
    public void setWorkType(String workType) { this.workType = workType; }

    public LocalDate getDesiredStartDate() { return desiredStartDate; }
    public void setDesiredStartDate(LocalDate desiredStartDate) { 
        this.desiredStartDate = desiredStartDate; 
    }

    public WorkRequestStatus getStatus() { return status; }
    public void setStatus(WorkRequestStatus status) { this.status = status; }

    public String getResidentEmail() { return residentEmail; }
    public void setResidentEmail(String residentEmail) { 
        this.residentEmail = residentEmail; 
    }

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
