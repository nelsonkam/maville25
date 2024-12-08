// Test pour récupérer toutes les requêtes de travaux 
// (Afficher la liste de toutes les requêtes de travaux)
package api.controllers;

import org.junit.jupiter.api.Test;
import models.WorkRequest;
import org.junit.jupiter.api.TestInstance;
import io.javalin.testtools.JavalinTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe teste la récupération de toutes les requêtes de travaux.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetAllWorkRequestsTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    /**
     * Teste la récupération de toutes les requêtes de travaux.
     */
    @Test
    public void testGetAllWorkRequests() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            // Création et soumission de plusieurs requêtes de travaux
            for (int i = 0; i < 3; i++) {
                WorkRequest workRequest = new WorkRequest(
                    "Travaux " + i,
                    "Description des travaux " + i,
                    "Voirie",
                    LocalDate.now().plusDays(10 + i),
                    "resident" + i + "@example.com"
                );
                String workRequestJson = JSON_MAPPER.writeValueAsString(workRequest);
                client.post("/work-requests", workRequestJson);
            }

            // Récupération de toutes les requêtes
            var response = client.get("/work-requests");
            assertEquals(200, response.code());
        });
    }
}