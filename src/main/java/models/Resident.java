package models;

import java.time.LocalDate;

public class Resident extends User {
    private LocalDate dateOfBirth;
    private String password;
    private String phoneNumber;
    private String address;

    public Resident() {}

    public Resident(String name, LocalDate dateOfBirth, String email, String password, String address) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Resident(String name, LocalDate dateOfBirth, String email, String password, String phoneNumber, String address) {
        this(name, dateOfBirth, email, password, address);
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
