package models;

import java.time.LocalDate;

/**
 * Cette classe représente un projet, 
 * qui est un type de travail géré par les intervenants
 */

public class Project {

    private Long id;
    private String title;
    private String description;
    private ProjectStatus projectStatus;
    private LocalDate desiredStartDate;

    /**
     *  Constructeur par défaut de la classe Project
     */
    public Project() {}

    /**
     * Constructeur de la class Project
     * 
     * @param title Le titre du projet
     * @param description La description du projet
     * @param projectStatus Le statut du projet
     * @param desiredStartDate La date désirée de début du projet
     */
    public Project(String title, String description, LocalDate desiredStartDate){
        this.title = title;
        this.description = description;
        this.projectStatus = ProjectStatus.PLANNED;
        this.desiredStartDate = desiredStartDate;
    }

    /**
     * Retourne le id du projet.
     *
     * @return Le id du projet.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retourne le titre du projet.
     *
     * @return Le titre du projet.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retourne la description du projet.
     *
     * @return La description du projet.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retourne le status du projet.
     *
     * @return Le statut du projet.
     */
    public String getProjectStatus() {
        return projectStatus.toString();
    }

    /**
     * Retourne la date désirée de début du projet.
     *
     * @return La date désirée de début du projet.
     */
    public LocalDate getDesiredStartDate() {
        return desiredStartDate;
    }

    /**
     * Définit le id du projet.
     *
     * @param id Le id du projet.
     */    
    public void setId(Long id) {
        this.id = id;
    }    

    /**
     * Définit le titre du projet.
     *
     * @param title Le titre du projet.
     */    
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Définit la description du projet.
     *
     * @param description La description du projet.
     */    
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Définit le status du projet.
     *
     * @param projectStatus Le status du projet.
     */
    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    
    /**
     * Définit la date de début désirée du projet.
     *
     * @param desiredStartDate La date de début désirée du projet.
     */
    public void setDesiredStartDate(LocalDate desiredStartDate) {
        this.desiredStartDate = desiredStartDate;
    }
}
