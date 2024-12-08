package api.controllers;

import api.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import io.javalin.testtools.JavalinTest;
import models.Intervenant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe teste les requêtes HTTP pour les intervenants.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntervenantControllerTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    private static final Faker faker = new Faker();
    private static final String TEST_PASSWORD = "testPassword123!";

    /**
     * Teste l'enregistrement d'un intervenant.
     */
    @Test
    public void testIntervenantRegistration() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            String email = faker.internet().emailAddress();
            
            Intervenant intervenant = new Intervenant(
                faker.company().name(),
                email,
                TEST_PASSWORD,
                "Entreprise publique",
                "12345678"
            );
            
            String intervenantJson = JSON_MAPPER.writeValueAsString(intervenant);
            
            var response = client.post("/intervenants", intervenantJson);
            assertEquals(201, response.code());
            
            var duplicateResponse = client.post("/intervenants", intervenantJson);
            assertEquals(400, duplicateResponse.code());
            
            // Test missing email
            intervenant.setEmail(null);
            var missingEmailResponse = client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant));
            assertEquals(400, missingEmailResponse.code());
            assertTrue(missingEmailResponse.body().string().contains("required fields"));

            // Test missing password
            intervenant.setEmail(email);
            intervenant.setPassword("");
            var missingPasswordResponse = client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant));
            assertEquals(400, missingPasswordResponse.code());
            assertTrue(missingPasswordResponse.body().string().contains("required fields"));
        });
    }

    /**
     * Teste la connexion d'un intervenant.
     */
    @Test
    public void testIntervenantLogin() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            String email = faker.internet().emailAddress();
            Intervenant intervenant = new Intervenant(
                faker.company().name(),
                email,
                TEST_PASSWORD,
                "Entrepreneur privé",
                "12345678"
            );
            
            client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant));
            
            var loginResponse = client.post("/intervenants/login", JSON_MAPPER.writeValueAsString(
                new LoginRequest(email, TEST_PASSWORD)
            ));
            assertEquals(200, loginResponse.code());
            
            var invalidPasswordResponse = client.post("/intervenants/login", JSON_MAPPER.writeValueAsString(
                new LoginRequest(email, "wrongpassword")
            ));
            assertEquals(401, invalidPasswordResponse.code());
            
            var nonExistentResponse = client.post("/intervenants/login", JSON_MAPPER.writeValueAsString(
                new LoginRequest("nonexistent@example.com", TEST_PASSWORD)
            ));
            assertEquals(401, nonExistentResponse.code());
        });
    }

    /**
     * Teste la récupération de tous les intervenants.
     */
    @Test
    public void testGetAllIntervenants() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            for (int i = 0; i < 3; i++) {
                Intervenant intervenant = new Intervenant(
                    faker.company().name(),
                    faker.internet().emailAddress(),
                    TEST_PASSWORD,
                    "Entreprise publique",
                    "12345678"
                );
                client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant));
            }
            
            var response = client.get("/intervenants");
            assertEquals(200, response.code());
            
            String body = response.body().string();
            assertTrue(body.contains("\"email\""));
            assertTrue(body.contains("\"type\""));
            assertTrue(body.contains("\"cityIdentifier\""));
            assertFalse(body.contains("\"password\""));
        });
    }
}