package main.java.models;

public abstract class User {
    protected String name;
    protected String email;

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String password) {
        // Cette méthode devrait être implémentée dans les classes filles
        return false;
    }
}
