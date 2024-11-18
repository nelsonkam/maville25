package models;

public class ConstructionWork {
    private String id;
    private String boroughId;
    private String currentStatus;
    private String reasonCategory;
    private String submitterCategory;
    private String organizationName;

    public ConstructionWork() {}

    public ConstructionWork(String id, String boroughId, String currentStatus,
                            String reasonCategory, String submitterCategory, String organizationName) {
        this.id = id;
        this.boroughId = boroughId;
        this.currentStatus = currentStatus;
        this.reasonCategory = reasonCategory;
        this.submitterCategory = submitterCategory;
        this.organizationName = organizationName;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBoroughId() { return boroughId; }
    public void setBoroughId(String boroughId) { this.boroughId = boroughId; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public String getReasonCategory() { return reasonCategory; }
    public void setReasonCategory(String reasonCategory) { this.reasonCategory = reasonCategory; }

    public String getSubmitterCategory() { return submitterCategory; }
    public void setSubmitterCategory(String submitterCategory) { this.submitterCategory = submitterCategory; }

    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }

    @Override
    public String toString() {
        return String.format("Travaux #%s\nArrondissement: %s\nStatut: %s\nType: %s\nIntervenant: %s (%s)",
            id, boroughId, currentStatus, reasonCategory, organizationName, submitterCategory);
    }
}
