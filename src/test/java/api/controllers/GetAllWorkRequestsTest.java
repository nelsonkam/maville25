//Test pour récupérer toutes les requêtes de travaux 
//(Afficher la liste de toutes les requêtes de travaux )
package api.controllers;

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import api.services.WorkRequestService;
import api.repositories.WorkRequestRepository;
import api.DatabaseManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllWorkRequestsTest {
    @Test
    public void testGetAllWorkRequests() {
        // Préparer un contrôleur avec des données existantes
        WorkRequestService service = new WorkRequestService(new WorkRequestRepository(DatabaseManager.getInstance()));
        List<WorkRequest> requests = service.getAllWorkRequests();

        // Vérifier les resultats
        assertNotNull(requests); // Vérifier que la liste n'est pas nulle
        assertTrue(requests.size() > 0); // Vérifier que la liste n'est pas vide
    }
}
