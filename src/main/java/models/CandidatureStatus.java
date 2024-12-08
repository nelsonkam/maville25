package models;

public enum CandidatureStatus {
    SUBMITTED ("Soumise"),
    WITHDRAWN ("Retirée"),
    SELECTED_BY_RESIDENT ("Sélectionnée par le résident"),
    CONFIRMED_BY_INTERVENANT ("Confirmée par l'intervenant");

    private final String displayName;

    CandidatureStatus(String displayName) {
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
