package models;

public enum WorkRequestStatus {
    PENDING("En attente"),
    ASSIGNED("Assigné"),
    IN_PROGRESS("En cours"),
    COMPLETED("Terminé"),
    CANCELLED("Annulé");

    private final String displayName;

    WorkRequestStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
