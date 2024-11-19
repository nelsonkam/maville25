package api.services;

import api.repositories.ResidentRepository;
import models.Resident;

import java.util.List;

public class ResidentService {
    private final ResidentRepository repository;

    public ResidentService(ResidentRepository repository) {
        this.repository = repository;
    }

    public void register(Resident resident) {
        if (repository.findByEmail(resident.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        repository.save(resident);
    }

    public Resident login(String email, String password) {
        return repository.findByEmail(email)
            .filter(resident -> resident.checkPassword(password))
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    }

    public List<Resident> getAllResidents() {
        return repository.findAll();
    }
}
