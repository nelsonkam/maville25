package api.services;

import api.repositories.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Project;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Cette classe fournit des services pour gérer les soumissions de projet.
 */

public class ProjectService {

    private final ProjectRepository repository;

    /**
     * Constructeur de la classe ProjectService.
     *
     * @param repository Le repository des projets.
     */
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }


    /**
     * Soumet un nouveau projet.
     *
     * @param project Le projet à soumettre.
     * @throws IllegalArgumentException Si le résident n'existe pas.
     * @throws RuntimeException Si une erreur survient lors de la soumission de la demande.
     */
    public void submitProject(Project project) {

        try {
            repository.save(project);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to submit project", e);
        }
    }

    /**
     * Récupère tous les projets.
     *
     * @return La liste de tous les projets.
     * @throws RuntimeException Si une erreur survient lors de la récupération des demandes.
     */
    public List<Project> getAllProjects() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch work projects", e);
        }
    }

    /**
     * Met à jour le statut d'un projet.
     *
     * @return La liste de tous les projets.
     * @throws RuntimeException Si une erreur survient lors de la récupération des demandes.
     */

     public void updateProjectStatus(Project updatedProject) {
        try{
            repository.update(updatedProject);
        } catch (SQLException e) {
            throw new RuntimeException("Database error while updating project status: " + e.getMessage());
        }
     }

    
}
