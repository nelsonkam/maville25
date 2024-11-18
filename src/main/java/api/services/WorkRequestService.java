package api.services;

import api.repositories.WorkRequestRepository;
import models.WorkRequest;

import java.sql.SQLException;
import java.util.List;

public class WorkRequestService {
    private final WorkRequestRepository repository;

    public WorkRequestService(WorkRequestRepository repository) {
        this.repository = repository;
    }

    public void submitRequest(WorkRequest request) {
        // Verify resident exists
        if (!residentExists(request.getResidentEmail())) {
            throw new IllegalArgumentException("Resident not found");
        }

        try {
            repository.save(request);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to submit work request", e);
        }
    }

    private boolean residentExists(String email) {
        try {
            return repository.residentExists(email);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to verify resident", e);
        }
    }

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

    public List<WorkRequest> getAllWorkRequests() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch work requests", e);
        }
    }


}
