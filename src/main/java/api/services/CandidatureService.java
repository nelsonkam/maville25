package api.services;

import api.repositories.CandidatureRepository;
import api.repositories.IntervenantRepository;
import api.repositories.WorkRequestRepository;
import models.Candidature;
import models.CandidatureStatus;
import models.Intervenant;
import models.WorkRequestStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CandidatureService {
    private final CandidatureRepository candidatureRepository;
    private final WorkRequestRepository workRequestRepository;
    private final IntervenantRepository intervenantRepository;

    public CandidatureService(CandidatureRepository candidatureRepository,
                              WorkRequestRepository workRequestRepository,
                              IntervenantRepository intervenantRepository) {
        this.candidatureRepository = candidatureRepository;
        this.workRequestRepository = workRequestRepository;
        this.intervenantRepository = intervenantRepository;
    }

    /**
     * Soumettre une candidature.
     * Vérifie l'existence de la WorkRequest, l'intervenant, la cohérence de l'email,
     * et l'absence de candidature SUBMITTED existante.
     */
    public Candidature submitCandidature(Long workRequestId, String intervenantEmail) {
        // Nettoyer et valider l'email
        intervenantEmail = validateAndNormalizeEmail(intervenantEmail);

        // Vérifier WorkRequest
        var wrOpt = safeFindWorkRequest(workRequestId);

        // Vérifier Intervenant
        var intervenantOpt = safeFindIntervenant(intervenantEmail);

        // Vérifier absence de candidature SUBMITTED existante
        try {
            var existing = candidatureRepository.findByWorkRequestAndIntervenant(workRequestId, intervenantEmail);
            if (existing.isPresent() && existing.get().getStatus() == CandidatureStatus.SUBMITTED) {
                throw new IllegalArgumentException("You have already submitted a candidature for this work request");
            }

            // Créer et sauvegarder
            Candidature c = new Candidature(workRequestId, intervenantEmail);
            candidatureRepository.save(c);
            return c;
        } catch (SQLException e) {
            throw new RuntimeException("Database error during candidature submission: " + e.getMessage(), e);
        }
    }

    /**
     * Retirer une candidature SUBMITTED.
     */
    public void withdrawCandidature(Long workRequestId, String intervenantEmail) {
        intervenantEmail = validateAndNormalizeEmail(intervenantEmail);
        var wrOpt = safeFindWorkRequest(workRequestId);
        var intervenantOpt = safeFindIntervenant(intervenantEmail);

        try {
            var candidatureOpt = candidatureRepository.findByWorkRequestAndIntervenant(workRequestId, intervenantEmail);
            if (candidatureOpt.isEmpty()) {
                throw new IllegalArgumentException("Candidature not found for this work request and intervenant");
            }

            Candidature candidature = candidatureOpt.get();
            if (candidature.getStatus() != CandidatureStatus.SUBMITTED) {
                throw new IllegalArgumentException("Only a SUBMITTED candidature can be withdrawn");
            }

            candidature.setStatus(CandidatureStatus.WITHDRAWN);
            candidatureRepository.update(candidature);
        } catch (SQLException e) {
            throw new RuntimeException("Database error during candidature withdrawal: " + e.getMessage(), e);
        }
    }

    /**
     * Lister les candidatures pour une WorkRequest.
     */
    public List<Candidature> getCandidaturesForWorkRequest(Long workRequestId) {
        // Vérifier WorkRequest
        var wrOpt = safeFindWorkRequest(workRequestId);
        try {
            return candidatureRepository.findByWorkRequestId(workRequestId);
        } catch (SQLException e) {
            throw new RuntimeException("Database error fetching candidatures: " + e.getMessage(), e);
        }
    }

    /**
     * Sélectionner une candidature (par le résident).
     * Vérification : WorkRequest existe, Candidature existe, statut SUBMITTED.
     */
    public void selectCandidatureByResident(Long workRequestId, Long candidatureId, String residentMessage) {
        var wrOpt = safeFindWorkRequest(workRequestId);

        try {
            var candidatureOpt = candidatureRepository.findById(candidatureId);
            if (candidatureOpt.isEmpty()) {
                throw new IllegalArgumentException("Candidature not found");
            }

            Candidature candidature = candidatureOpt.get();
            if (!candidature.getWorkRequestId().equals(workRequestId)) {
                throw new IllegalArgumentException("Candidature does not belong to this work request");
            }

            if (candidature.getStatus() != CandidatureStatus.SUBMITTED) {
                throw new IllegalStateException("Only a SUBMITTED candidature can be selected");
            }

            candidature.setStatus(CandidatureStatus.SELECTED_BY_RESIDENT);
            candidature.setResidentMessage(residentMessage);
            candidatureRepository.update(candidature);
        } catch (SQLException e) {
            throw new RuntimeException("Database error selecting candidature: " + e.getMessage(), e);
        }
    }

    /**
     * Confirmer une candidature (par l’intervenant).
     * Vérifications : WorkRequest existe, Candidature existe et appartient à l'intervenant et à la WorkRequest,
     * statut SELECTED_BY_RESIDENT requis, puis mise à jour du statut en IN_PROGRESS pour la WorkRequest.
     */
    public void confirmCandidatureByIntervenant(Long workRequestId, Long candidatureId, String intervenantEmail) {
        intervenantEmail = validateAndNormalizeEmail(intervenantEmail);
        var wrOpt = safeFindWorkRequest(workRequestId);
        var intervenantOpt = safeFindIntervenant(intervenantEmail);

        try {
            var cOpt = candidatureRepository.findById(candidatureId);
            if (cOpt.isEmpty()) {
                throw new IllegalArgumentException("Candidature not found");
            }

            Candidature c = cOpt.get();
            if (!c.getIntervenantEmail().equalsIgnoreCase(intervenantEmail)) {
                throw new IllegalArgumentException("Candidature does not belong to this intervenant");
            }

            if (!c.getWorkRequestId().equals(workRequestId)) {
                throw new IllegalArgumentException("Candidature does not belong to this work request");
            }

            if (c.getStatus() != CandidatureStatus.SELECTED_BY_RESIDENT) {
                throw new IllegalStateException("Only a SELECTED_BY_RESIDENT candidature can be confirmed");
            }

            c.setStatus(CandidatureStatus.CONFIRMED_BY_INTERVENANT);
            c.setConfirmedByIntervenant(true);
            candidatureRepository.update(c);

            // Mettre à jour la WorkRequest en IN_PROGRESS
            var updatedWr = wrOpt.get();
            updatedWr.setStatus(WorkRequestStatus.IN_PROGRESS);
            workRequestRepository.update(updatedWr);

        } catch (SQLException e) {
            throw new RuntimeException("Database error confirming candidature: " + e.getMessage(), e);
        }
    }

    /**
     * Méthode utilitaire pour vérifier que la WorkRequest existe.
     * Renvoie l'Optional<WorkRequest> si trouvé, sinon lance IllegalArgumentException.
     */
    private Optional<models.WorkRequest> safeFindWorkRequest(Long workRequestId) {
        try {
            var wrOpt = workRequestRepository.findById(workRequestId);
            if (wrOpt.isEmpty()) {
                throw new IllegalArgumentException("Work request not found");
            }
            return wrOpt;
        } catch (SQLException e) {
            throw new RuntimeException("Database error verifying work request: " + e.getMessage(), e);
        }
    }

    public List<Candidature> getCandidaturesByIntervenant(String intervenantEmail) {
        intervenantEmail = validateAndNormalizeEmail(intervenantEmail);
    
        try {
            return candidatureRepository.findByIntervenantEmail(intervenantEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des candidatures : " + e.getMessage(), e);
        }
    }

    /**
     * Méthode utilitaire pour vérifier que l'Intervenant existe.
     */
    private Optional<Intervenant> safeFindIntervenant(String email) {
        var intervenantOpt = intervenantRepository.findByEmail(email);
        if (intervenantOpt.isEmpty()) {
            throw new IllegalArgumentException("Intervenant not found");
        }
        return intervenantOpt;
    }
    

    /**
     * Valide et normalise un email :
     * - Vérifie qu'il n'est pas null ou vide.
     * - Met en minuscule et trim.
     */
    private String validateAndNormalizeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Intervenant email is required");
        }
        email = email.trim().toLowerCase();
        // Ici, vous pourriez ajouter d'autres validations, par exemple vérifier un pattern regex
        return email;
    }

    /**
     * Cette méthode permet à un résident de faire le suivi 
     *  des candidatures pour les travaux qu'il a soumis
     * 
     */

    public List<Candidature> getCandidaturesByWorkRequestAndResidentEmail(String residentEmail) {
        residentEmail = validateAndNormalizeEmail(residentEmail);
    
        try {
            return candidatureRepository.findByWorkRequestAndResidentEmail(residentEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des candidatures : " + e.getMessage(), e);
        }
    }
}
