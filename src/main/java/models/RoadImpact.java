package models;

public class RoadImpact {
    private String requestId;
    private String streetId;
    private String streetName;
    private String impactType;

    public RoadImpact() {}

    public RoadImpact(String requestId, String streetId, String streetName, String impactType) {
        this.requestId = requestId;
        this.streetId = streetId;
        this.streetName = streetName;
        this.impactType = impactType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getImpactType() {
        return impactType;
    }

    public void setImpactType(String impactType) {
        this.impactType = impactType;
    }

    @Override
    public String toString() {
        return String.format("Rue: %s\nType d'impact: %s", 
            streetName, impactType);
    }
}
