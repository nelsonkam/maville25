package api.services;

import api.repositories.IntervenantRepository;
import models.Intervenant;

import java.util.List;

public class IntervenantService {
    private final IntervenantRepository repository;

    public IntervenantService(IntervenantRepository repository) {
        this.repository = repository;
    }

    public void register(Intervenant intervenant) {
        if (repository.findByEmail(intervenant.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        repository.save(intervenant);
    }

    public Intervenant login(String email, String password) {
        return repository.findByEmail(email)
            .filter(intervenant -> intervenant.checkPassword(password))
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    public List<Intervenant> getAllIntervenants() {
        return repository.findAll();
    }
}
