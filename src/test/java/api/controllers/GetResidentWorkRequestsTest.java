// Test pour récupérer toutes les requêtes d’un résident
package api.controllers;

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import api.services.WorkRequestService;
import api.repositories.WorkRequestRepository;
import api.DatabaseManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetResidentWorkRequestsTest {
    @Test
    public void testGetResidentWorkRequests() {
        // Créer des données fictives
        String residentEmail = "resident@example.com";
        List<WorkRequest> mockRequests = List.of(
                new WorkRequest("Resident Work", "Work Description", "Type1", LocalDate.now(), residentEmail));

        // Simuler le comportement du repository
        WorkRequestRepository mockRepository = Mockito.mock(WorkRequestRepository.class);
        Mockito.when(mockRepository.getByResidentEmail(residentEmail)).thenReturn(mockRequests);

        // Utiliser le service avec le mock
        WorkRequestService service = new WorkRequestService(mockRepository);
        List<WorkRequest> requests = service.getResidentRequests(residentEmail);

        // Vérifier les résultats
        assertNotNull(requests);
        assertEquals(1, requests.size());
        assertEquals(residentEmail, requests.get(0).getResidentEmail());

    }
}
