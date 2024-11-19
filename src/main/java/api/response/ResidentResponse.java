package api.response;

import models.Resident;

import java.time.LocalDate;

public class ResidentResponse {
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;

    public ResidentResponse(Resident resident) {
        this.name = resident.getName();
        this.email = resident.getEmail();
        this.dateOfBirth = resident.getDateOfBirth();
        this.phoneNumber = resident.getPhoneNumber();
        this.address = resident.getAddress();
    }

    // Default constructor for Jackson
    public ResidentResponse() {}

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
