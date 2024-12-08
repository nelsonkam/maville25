package api.response;

import models.Resident;

import java.time.LocalDate;

/**
 * Cette classe représente la réponse contenant les informations d'un résident.
 */
public class ResidentResponse {
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String address;

    /**
     * Constructeur de la classe ResidentResponse.
     * Initialise les champs à partir d'un objet Resident.
     *
     * @param resident Le résident dont les informations sont utilisées pour initialiser la réponse.
     */
    public ResidentResponse(Resident resident) {
        this.name = resident.getName();
        this.email = resident.getEmail();
        this.dateOfBirth = resident.getDateOfBirth();
        this.phoneNumber = resident.getPhoneNumber();
        this.address = resident.getAddress();
    }

    /**
     * Constructeur par défaut pour Jackson.
     */
    public ResidentResponse() {}

    /**
     * Retourne le nom du résident.
     *
     * @return Le nom du résident.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom du résident.
     *
     * @param name Le nom du résident.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne l'email du résident.
     *
     * @return L'email du résident.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'email du résident.
     *
     * @param email L'email du résident.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne la date de naissance du résident.
     *
     * @return La date de naissance du résident.
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Définit la date de naissance du résident.
     *
     * @param dateOfBirth La date de naissance du résident.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Retourne le numéro de téléphone du résident.
     *
     * @return Le numéro de téléphone du résident.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Définit le numéro de téléphone du résident.
     *
     * @param phoneNumber Le numéro de téléphone du résident.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retourne l'adresse du résident.
     *
     * @return L'adresse du résident.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse du résident.
     *
     * @param address L'adresse du résident.
     */
    public void setAddress(String address) {
        this.address = address;
    }
}