package models;

public class Intervenant extends User {
    private String password;
    private String type;
    private String cityIdentifier;

    public Intervenant() {}

    public Intervenant(String name, String email, String password, String type, String cityIdentifier) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.cityIdentifier = cityIdentifier;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

}
