// Test pour récupérer toutes les requêtes d’un résident
package api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import models.Resident;
import models.WorkRequest;
import io.javalin.testtools.JavalinTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe teste la récupération de toutes les requêtes d'un résident.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetResidentWorkRequestsTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    /**
     * Teste la récupération de toutes les requêtes d'un résident.
     */
    @Test
    public void testGetResidentWorkRequests() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            // Création d'un résident fictif
            Resident resident = new Resident(
                "Jean Dupont",
                LocalDate.now().minusYears(30),
                "jean.dupont@example.com",
                "mot_de_passe_test",
                "514-123-4567",
                "123 Rue Exemple"
            );
            String residentJson = JSON_MAPPER.writeValueAsString(resident);
            client.post("/residents", residentJson);

            // Création et soumission d'une demande de travaux pour ce résident
            WorkRequest workRequest = new WorkRequest(
                "Réparation trottoirs",
                "Travaux importants pour le trottoir",
                "Voirie",
                LocalDate.now().plusDays(10),
                "jean.dupont@example.com"
            );

            String workRequestJson = JSON_MAPPER.writeValueAsString(workRequest);
            client.post("/work-requests", workRequestJson);

            // Récupération des requêtes pour le résident
            var response = client.get("/work-requests/resident/jean.dupont@example.com");
            assertEquals(200, response.code());
        });
    }
}