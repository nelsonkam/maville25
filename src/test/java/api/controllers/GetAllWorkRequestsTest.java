//Test pour récupérer toutes les requêtes de travaux 
//(Afficher la liste de toutes les requêtes de travaux )
package api.controllers;

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import api.services.WorkRequestService;
import api.repositories.WorkRequestRepository;
import api.DatabaseManager;

import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*; 
import org.mockito.Mock;             
import org.mockito.Mockito;          
import org.junit.jupiter.api.Test;   
import org.junit.jupiter.api.extension.ExtendWith; 
import org.mockito.junit.jupiter.MockitoExtension; 

import static org.junit.jupiter.api.Assertions.*;
@Ignore
public class GetAllWorkRequestsTest {
    @Test
    public void testGetAllWorkRequests() {

        // Créer des données fictives
        List<WorkRequest> mockRequests = List.of(
                new WorkRequest("Test Work", "Work Description", "Type1", LocalDate.now(), "resident@example.com"));

        // Simuler le comportement du repository
        WorkRequestRepository mockRepository = Mockito.mock(WorkRequestRepository.class);
        Mockito.when(mockRepository.getAll()).thenReturn(mockRequests);

        // Utiliser le service avec le mock
        WorkRequestService service = new WorkRequestService(mockRepository);
        List<WorkRequest> requests = service.getAllWorkRequests();

        // Vérifier les résultats
        assertNotNull(requests);
        assertEquals(1, requests.size());
        assertEquals("Test Work", requests.get(0).getTitle());
    }
}
