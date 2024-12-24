package api.services;

import api.repositories.WorkPreferenceRepository;
import models.WorkPreference;

import java.sql.SQLException;
import java.util.List;

/**
 * Service pour gérer les préférences horaires des résidents.
 */
public class WorkPreferenceService {
    private final WorkPreferenceRepository repository;

    /**
     * Constructeur.
     *
     * @param repository Le dépôt pour accéder aux données.
     */
    public WorkPreferenceService(WorkPreferenceRepository repository) {
        this.repository = repository;
    }

    /**
     * Sauvegarde une nouvelle préférence horaire.
     *
     * @param preference L'objet WorkPreference à sauvegarder.
     */
    public void savePreference(WorkPreference preference) {
        try {
            repository.save(preference);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save work preference", e);
        }
    }

    /**
     * Met à jour une préférence horaire existante.
     *
     * @param preference L'objet WorkPreference à mettre à jour.
     */
    public void updatePreference(WorkPreference preference) {
        try {
            repository.update(preference);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update work preference", e);
        }
    }

    /**
     * Récupère toutes les préférences horaires d'un résident.
     *
     * @param residentEmail L'email du résident.
     * @return Une liste de WorkPreference.
     */
    public List<WorkPreference> getPreferencesByResident(String residentEmail) {
        try {
            return repository.findByResidentEmail(residentEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch work preferences", e);
        }
    }
}
