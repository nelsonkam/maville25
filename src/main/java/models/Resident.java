package models;

import java.time.LocalDate;

/**
 * Cette classe représente un résident, qui est un type d'utilisateur.
 */
public class Resident extends User {
    private LocalDate dateOfBirth;
    private String password;
    private String phoneNumber;
    private String address;

    /**
     * Constructeur par défaut de la classe Resident.
     */
    public Resident() {}

    /**
     * Constructeur de la classe Resident.
     *
     * @param name Le nom du résident.
     * @param dateOfBirth La date de naissance du résident.
     * @param email L'email du résident.
     * @param password Le mot de passe du résident.
     * @param address L'adresse du résident.
     */
    public Resident(String name, LocalDate dateOfBirth, String email, String password, String address) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    /**
     * Constructeur de la classe Resident avec numéro de téléphone.
     *
     * @param name Le nom du résident.
     * @param dateOfBirth La date de naissance du résident.
     * @param email L'email du résident.
     * @param password Le mot de passe du résident.
     * @param phoneNumber Le numéro de téléphone du résident.
     * @param address L'adresse du résident.
     */
    public Resident(String name, LocalDate dateOfBirth, String email, String password, String phoneNumber, String address) {
        this(name, dateOfBirth, email, password, address);
        this.phoneNumber = phoneNumber;
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
     * Retourne le numéro de téléphone du résident.
     *
     * @return Le numéro de téléphone du résident.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retourne le mot de passe du résident.
     *
     * @return Le mot de passe du résident.
     */
    public String getPassword() {
        return password;
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
     * Définit la date de naissance du résident.
     *
     * @param dateOfBirth La date de naissance du résident.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Définit le mot de passe du résident.
     *
     * @param password Le mot de passe du résident.
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Définit l'adresse du résident.
     *
     * @param address L'adresse du résident.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Vérifie si le mot de passe fourni correspond au mot de passe du résident.
     *
     * @param password Le mot de passe à vérifier.
     * @return true si le mot de passe correspond, false sinon.
     */
    @Override
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}