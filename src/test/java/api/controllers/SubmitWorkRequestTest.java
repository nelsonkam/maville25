package api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import models.Resident;
import models.WorkRequest;
import io.javalin.testtools.JavalinTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Cette classe teste la soumission des demandes de travaux.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubmitWorkRequestTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    /**
     * Teste la soumission d'une demande de travaux.
     */
    @Test
    public void testSubmitWorkRequest() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            // Création d'un résident fictif
            Resident resident = new Resident(
                "Marie Curie",
                LocalDate.now().minusYears(35),
                "marie.curie@example.com",
                "mot_de_passe_test",
                "514-321-9876",
                "456 Rue Exemple"
            );

            String residentJson = JSON_MAPPER.writeValueAsString(resident);
            client.post("/residents", residentJson);

            // Création d'une requête de travaux fictive
            WorkRequest workRequest = new WorkRequest(
                "Remplacement lampadaires",
                "Lampadaires cassés sur la rue",
                "Éclairage public",
                LocalDate.now().plusDays(15),
                "marie.curie@example.com"
            );
            String workRequestJson = JSON_MAPPER.writeValueAsString(workRequest);

            // Soumission de la requête
            var response = client.post("/work-requests", workRequestJson);
            assertEquals(201, response.code());
        });
    }
}