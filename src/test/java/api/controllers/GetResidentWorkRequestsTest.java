// Test pour récupérer toutes les requêtes d’un résident
package api.controllers;

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import api.services.WorkRequestService;
import api.repositories.WorkRequestRepository;
import api.DatabaseManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetResidentWorkRequestsTest {
    @Test
    public void testGetResidentWorkRequests() {
        // Préparer les données pour un residents
        String residentEmail = "resident@example.com";
        WorkRequestService service = new WorkRequestService(new WorkRequestRepository(DatabaseManager.getInstance()));

        // Pour un resident donné, récupérer toutes les requêtes de travaux
        List<WorkRequest> requests = service.getResidentRequests(residentEmail);

        // Vérifier les resultats
        assertNotNull(requests); // Vérifier que la liste n'est pas nulle
        for (WorkRequest request : requests) {
            assertEquals(residentEmail, request.getResidentEmail()); // Vérifier que toutes les requêtes appartiennent
                                                                     // au même résident
        }

    }
}
