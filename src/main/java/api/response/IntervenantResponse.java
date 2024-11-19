package api.response;

import models.Intervenant;

public class IntervenantResponse {
    private String name;
    private String email;
    private String type;
    private String cityIdentifier;

    public IntervenantResponse(Intervenant intervenant) {
        this.name = intervenant.getName();
        this.email = intervenant.getEmail();
        this.type = intervenant.getType();
        this.cityIdentifier = intervenant.getCityIdentifier();
    }

    // Default constructor for Jackson
    public IntervenantResponse() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityIdentifier() {
        return cityIdentifier;
    }

    public void setCityIdentifier(String cityIdentifier) {
        this.cityIdentifier = cityIdentifier;
    }
}
