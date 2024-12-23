package api.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.testtools.JavalinTest;
import models.Intervenant;
import models.Resident;
import models.WorkRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe teste le processus d'intégration des candidatures.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CandidatureProcessIntegrationTest extends BaseControllerTest {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final String RESIDENT_EMAIL = "resident@example.com";
    private static final String INTERVENANT_EMAIL = "intervenant@example.com";
    private static final String TEST_PASSWORD = "password123";

    private void createResident(io.javalin.testtools.HttpClient client) throws Exception {
        Resident resident = new Resident(
            "Jean Dupont",
            LocalDate.now().minusYears(30),
            RESIDENT_EMAIL,
            TEST_PASSWORD,
            "514-123-4567",
            "123 Rue Exemple"
        );
        var response = client.post("/residents", JSON_MAPPER.writeValueAsString(resident));
        assertEquals(201, response.code(), "Création résident échouée. Body: " + response.body().string());
    }

    private void createIntervenant(io.javalin.testtools.HttpClient client) throws Exception {
        Intervenant intervenant = new Intervenant(
            "Entreprise Lumière",
            INTERVENANT_EMAIL,
            TEST_PASSWORD,
            "Entreprise publique",
            "12345678"
        );
        var response = client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant));
        assertEquals(201, response.code(), "Création intervenant échouée. Body: " + response.body().string());
    }

    private Long createWorkRequest(io.javalin.testtools.HttpClient client) throws Exception {
        WorkRequest wr = new WorkRequest(
            "Remplacement lampadaire",
            "Lampadaire cassé à réparer",
            "Éclairage public",
            LocalDate.now().plusDays(10),
            RESIDENT_EMAIL
        );
        var response = client.post("/work-requests", JSON_MAPPER.writeValueAsString(wr));
        String body = response.body().string();
        assertEquals(201, response.code(), "Création WorkRequest échouée. Body: " + body);
        JsonNode createdWr = JSON_MAPPER.readTree(body);
        Long workRequestId = createdWr.get("id").asLong();
        return workRequestId;
    }

    /**
     * Test 1 : Soumettre une candidature avec succès.
     */
    @Test
    void testSubmitCandidatureSuccess() throws Exception {
        JavalinTest.test(createTestApp(), (server, client) -> {
            createResident(client);
            createIntervenant(client);
            Long workRequestId = createWorkRequest(client);

            String candidatureJson = """
                {
                  "intervenantEmail": "%s"
                }
                """.formatted(INTERVENANT_EMAIL);

            var submitCandidatureResponse = client.post("/work-requests/" + workRequestId + "/candidatures", candidatureJson);
            String body = submitCandidatureResponse.body().string();
            assertEquals(201, submitCandidatureResponse.code(), "Soumission candidature échouée (attendu 201). Body: " + body);

            var getCandidaturesResponse = client.get("/work-requests/" + workRequestId + "/candidatures");
            String getCandidaturesBody = getCandidaturesResponse.body().string();
            assertEquals(200, getCandidaturesResponse.code(), "Récupération candidatures échouée. Body: " + getCandidaturesBody);

            JsonNode candidaturesArray = JSON_MAPPER.readTree(getCandidaturesBody);
            assertTrue(candidaturesArray.isArray(), "Le résultat ne semble pas être un tableau. Body: " + getCandidaturesBody);
            assertEquals(1, candidaturesArray.size(), "Devrait avoir 1 candidature. Body: " + getCandidaturesBody);
            assertEquals(INTERVENANT_EMAIL, candidaturesArray.get(0).get("intervenantEmail").asText());
            assertEquals("SUBMITTED", candidaturesArray.get(0).get("status").asText());
        });
    }

    /**
     * Test 2 : Retirer une candidature SUBMITTED.
     */
    @Test
    void testWithdrawCandidatureSuccess() throws Exception {
        JavalinTest.test(createTestApp(), (server, client) -> {
            createResident(client);
            createIntervenant(client);
            Long workRequestId = createWorkRequest(client);

            String candidatureJson = """
                {
                  "intervenantEmail": "%s"
                }
                """.formatted(INTERVENANT_EMAIL);

            var submitResponse = client.post("/work-requests/" + workRequestId + "/candidatures", candidatureJson);
            String submitBody = submitResponse.body().string();
            assertEquals(201, submitResponse.code(), "Soumission candidature échouée. Body: " + submitBody);

            var withdrawResponse = client.delete("/work-requests/" + workRequestId + "/candidatures?intervenantEmail=" + INTERVENANT_EMAIL);
            String withdrawBody = withdrawResponse.body().string();
            assertEquals(200, withdrawResponse.code(), "Retrait candidature échouée. Body: " + withdrawBody);

            var getCandidaturesResponse = client.get("/work-requests/" + workRequestId + "/candidatures");
            String getCandidaturesBody = getCandidaturesResponse.body().string();
            assertEquals(200, getCandidaturesResponse.code(), "Récupération candidatures échouée. Body: " + getCandidaturesBody);
            JsonNode candidaturesArray = JSON_MAPPER.readTree(getCandidaturesBody);
            assertEquals(1, candidaturesArray.size(), "Devrait avoir 1 candidature après retrait. Body: " + getCandidaturesBody);
            assertEquals("WITHDRAWN", candidaturesArray.get(0).get("status").asText(), "La candidature devrait être WITHDRAWN. Body: " + getCandidaturesBody);
        });
    }

    /**
     * Test 3 : Lister plusieurs candidatures.
     */
    @Test
    void testListMultipleCandidatures() throws Exception {
        JavalinTest.test(createTestApp(), (server, client) -> {
            createResident(client);

            // Créer deux intervenants distincts
            String intervenantEmail1 = "intervenant1@example.com";
            Intervenant intervenant1 = new Intervenant("Entreprise1", intervenantEmail1, TEST_PASSWORD, "Entreprise publique", "11111111");
            var i1Resp = client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant1));
            String i1Body = i1Resp.body().string();
            assertEquals(201, i1Resp.code(), "Création intervenant1 échouée. Body: " + i1Body);

            String intervenantEmail2 = "intervenant2@example.com";
            Intervenant intervenant2 = new Intervenant("Entreprise2", intervenantEmail2, TEST_PASSWORD, "Entreprise publique", "22222222");
            var i2Resp = client.post("/intervenants", JSON_MAPPER.writeValueAsString(intervenant2));
            String i2Body = i2Resp.body().string();
            assertEquals(201, i2Resp.code(), "Création intervenant2 échouée. Body: " + i2Body);

            Long workRequestId = createWorkRequest(client);

            String candidature1 = """
                {
                  "intervenantEmail": "%s"
                }
                """.formatted(intervenantEmail1);
            String candidature2 = """
                {
                  "intervenantEmail": "%s"
                }
                """.formatted(intervenantEmail2);

            var c1Response = client.post("/work-requests/" + workRequestId + "/candidatures", candidature1);
            String c1Body = c1Response.body().string();
            assertEquals(201, c1Response.code(), "Soumission candidature 1 échouée. Body: " + c1Body);

            var c2Response = client.post("/work-requests/" + workRequestId + "/candidatures", candidature2);
            String c2Body = c2Response.body().string();
            assertEquals(201, c2Response.code(), "Soumission candidature 2 échouée. Body: " + c2Body);

            var getCandidaturesResponse = client.get("/work-requests/" + workRequestId + "/candidatures");
            String getCandidaturesBody = getCandidaturesResponse.body().string();
            assertEquals(200, getCandidaturesResponse.code(), "Récupération candidatures échouée. Body: " + getCandidaturesBody);
            JsonNode candidaturesArray = JSON_MAPPER.readTree(getCandidaturesBody);
            assertTrue(candidaturesArray.isArray(), "Résultat non array. Body: " + getCandidaturesBody);
            assertEquals(2, candidaturesArray.size(), "Devrait avoir 2 candidatures. Body: " + getCandidaturesBody);

            boolean hasIntervenant1 = false;
            boolean hasIntervenant2 = false;
            for (JsonNode c : candidaturesArray) {
                String email = c.get("intervenantEmail").asText();
                if (email.equals(intervenantEmail1)) hasIntervenant1 = true;
                if (email.equals(intervenantEmail2)) hasIntervenant2 = true;
            }

            assertTrue(hasIntervenant1, "Manque candidature intervenant1. Body: " + getCandidaturesBody);
            assertTrue(hasIntervenant2, "Manque candidature intervenant2. Body: " + getCandidaturesBody);
        });
    }
}