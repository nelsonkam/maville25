
//Test pour soumettre une nouvelle requête de travaux

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import services.WorkRequestService;
import repositories.WorkRequestRepository;
import api.DatabaseManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SubmitWorkRequestTest {
    @Test
    public void testSubmitWorkRequest() {
        // Preparer unec noivelle requete de travaux
        WorkRequest request = new WorkRequest("New Title", "New Description", "Type1", LocalDate.now(),
                "resident@example.com");
        WorkRequestService service = new WorkRequestService(new WorkRequestRepository(DatabaseManager.getInstance()));

        // Soumettre la requete
        service.submiRequest(request);

        // Verifier qu'elle a été ajoutée
        List<WorkRequest> requests = service.getResidentRequests("resident@example.com");
        assertTrue(requests.stream().anyMatch(r -> r.getTitle().equals("New Title"))); // Vérifie que la requête a été
                                                                                       // ajoutée
    }

}
