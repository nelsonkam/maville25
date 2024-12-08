package api.services;

import api.repositories.IntervenantRepository;
import models.Intervenant;

import java.util.List;

/**
 * Cette classe fournit des services pour gérer les intervenants.
 */
public class IntervenantService {
    private final IntervenantRepository repository;

    /**
     * Constructeur de la classe IntervenantService.
     *
     * @param repository Le repository des intervenants.
     */
    public IntervenantService(IntervenantRepository repository) {
        this.repository = repository;
    }

    /**
     * Enregistre un nouvel intervenant.
     *
     * @param intervenant L'intervenant à enregistrer.
     * @throws IllegalArgumentException Si l'email existe déjà.
     */
    public void register(Intervenant intervenant) {
        if (repository.findByEmail(intervenant.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        repository.save(intervenant);
    }

    /**
     * Authentifie un intervenant.
     *
     * @param email L'email de l'intervenant.
     * @param password Le mot de passe de l'intervenant.
     * @return L'intervenant authentifié.
     * @throws IllegalArgumentException Si les identifiants sont invalides.
     */
    public Intervenant login(String email, String password) {
        return repository.findByEmail(email)
            .filter(intervenant -> intervenant.checkPassword(password))
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    /**
     * Récupère tous les intervenants.
     *
     * @return La liste de tous les intervenants.
     */
    public List<Intervenant> getAllIntervenants() {
        return repository.findAll();
    }
}