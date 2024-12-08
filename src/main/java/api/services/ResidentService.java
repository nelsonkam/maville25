package api.services;

import api.repositories.ResidentRepository;
import models.Resident;

import java.util.List;

/**
 * Cette classe fournit des services pour gérer les résidents.
 */
public class ResidentService {
    private final ResidentRepository repository;

    /**
     * Constructeur de la classe ResidentService.
     *
     * @param repository Le repository des résidents.
     */
    public ResidentService(ResidentRepository repository) {
        this.repository = repository;
    }

    /**
     * Enregistre un nouveau résident.
     *
     * @param resident Le résident à enregistrer.
     * @throws IllegalArgumentException Si l'email existe déjà.
     */
    public void register(Resident resident) {
        if (repository.findByEmail(resident.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        repository.save(resident);
    }

    /**
     * Authentifie un résident.
     *
     * @param email L'email du résident.
     * @param password Le mot de passe du résident.
     * @return Le résident authentifié.
     * @throws IllegalArgumentException Si les identifiants sont invalides.
     */
    public Resident login(String email, String password) {
        return repository.findByEmail(email)
            .filter(resident -> resident.checkPassword(password))
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    /**
     * Récupère tous les résidents.
     *
     * @return La liste de tous les résidents.
     */
    public List<Resident> getAllResidents() {
        return repository.findAll();
    }
}