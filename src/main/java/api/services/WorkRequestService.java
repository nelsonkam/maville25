package api.services;

import api.repositories.WorkRequestRepository;
import models.WorkRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Cette classe fournit des services pour gérer les demandes de travaux.
 */
public class WorkRequestService {
    private final WorkRequestRepository repository;

    /**
     * Constructeur de la classe WorkRequestService.
     *
     * @param repository Le repository des demandes de travaux.
     */
    public WorkRequestService(WorkRequestRepository repository) {
        this.repository = repository;
    }

    /**
     * Soumet une nouvelle demande de travaux.
     *
     * @param request La demande de travaux à soumettre.
     * @throws IllegalArgumentException Si le résident n'existe pas.
     * @throws RuntimeException Si une erreur survient lors de la soumission de la demande.
     */
    public void submitRequest(WorkRequest request) {
        // Vérifie que le résident existe
        if (!residentExists(request.getResidentEmail())) {
            throw new IllegalArgumentException("Resident not found");
        }

        try {
            repository.save(request);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to submit work request", e);
        }
    }

    /**
     * Vérifie si un résident existe.
     *
     * @param email L'email du résident.
     * @return true si le résident existe, false sinon.
     * @throws RuntimeException Si une erreur survient lors de la vérification.
     */
    private boolean residentExists(String email) {
        try {
            return repository.residentExists(email);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to verify resident", e);
        }
    }

    /**
     * Récupère les demandes de travaux d'un résident.
     *
     * @param residentEmail L'email du résident.
     * @return La liste des demandes de travaux du résident.
     * @throws IllegalArgumentException Si le résident n'existe pas.
     * @throws RuntimeException Si une erreur survient lors de la récupération des demandes.
     */
    public List<WorkRequest> getResidentRequests(String residentEmail) {
        if (!residentExists(residentEmail)) {
            throw new IllegalArgumentException("Resident not found");
        }
        try {
            return repository.findByResidentEmail(residentEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch resident's work requests", e);
        }
    }

    /**
     * Récupère toutes les demandes de travaux.
     *
     * @return La liste de toutes les demandes de travaux.
     * @throws RuntimeException Si une erreur survient lors de la récupération des demandes.
     */
    public List<WorkRequest> getAllWorkRequests() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch work requests", e);
        }
    }
}