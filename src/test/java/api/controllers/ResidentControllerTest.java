package api.controllers;

import api.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import io.javalin.testtools.JavalinTest;
import models.Resident;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe teste les requêtes HTTP pour les résidents.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResidentControllerTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    private static final Faker faker = new Faker();
    private static final String TEST_PASSWORD = "testPassword123!";

    /**
     * Teste l'enregistrement d'un résident.
     */
    @Test
    public void testResidentRegistration() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            LocalDate dob = LocalDate.now().minusYears(20);
            String email = faker.internet().emailAddress();
            
            Resident resident = new Resident(
                faker.name().fullName(),
                dob,
                email,
                TEST_PASSWORD,
                faker.phoneNumber().phoneNumber(),
                faker.address().fullAddress()
            );
            
            String residentJson = JSON_MAPPER.writeValueAsString(resident);
            
            var response = client.post("/residents", residentJson);
            assertEquals(201, response.code());
            
            var duplicateResponse = client.post("/residents", residentJson);
            assertEquals(400, duplicateResponse.code());
            
            // Test missing email
            resident.setEmail(null);
            var missingEmailResponse = client.post("/residents", JSON_MAPPER.writeValueAsString(resident));
            assertEquals(400, missingEmailResponse.code());
            assertTrue(missingEmailResponse.body().string().contains("required fields"));

            // Test missing password
            resident.setEmail(email);
            resident.setPassword("");
            var missingPasswordResponse = client.post("/residents", JSON_MAPPER.writeValueAsString(resident));
            assertEquals(400, missingPasswordResponse.code());
            assertTrue(missingPasswordResponse.body().string().contains("required fields"));
        });
    }

    /**
     * Teste la connexion d'un résident.
     */
    @Test
    public void testResidentLogin() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            String email = faker.internet().emailAddress();
            Resident resident = new Resident(
                faker.name().fullName(),
                LocalDate.now().minusYears(20),
                email,
                TEST_PASSWORD,
                faker.phoneNumber().phoneNumber(),
                faker.address().fullAddress()
            );
            
            client.post("/residents", JSON_MAPPER.writeValueAsString(resident));
            
            var loginResponse = client.post("/residents/login", JSON_MAPPER.writeValueAsString(
                new LoginRequest(email, TEST_PASSWORD)
            ));
            assertEquals(200, loginResponse.code());
            
            var invalidPasswordResponse = client.post("/residents/login", JSON_MAPPER.writeValueAsString(
                new LoginRequest(email, "wrongpassword")
            ));
            assertEquals(401, invalidPasswordResponse.code());
            
            var nonExistentResponse = client.post("/residents/login", JSON_MAPPER.writeValueAsString(
                new LoginRequest("nonexistent@example.com", TEST_PASSWORD)
            ));
            assertEquals(401, nonExistentResponse.code());
        });
    }

    /**
     * Teste la récupération de tous les résidents.
     */
    @Test
    public void testGetAllResidents() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            for (int i = 0; i < 3; i++) {
                Resident resident = new Resident(
                    faker.name().fullName(),
                    LocalDate.now().minusYears(20 + i),
                    faker.internet().emailAddress(),
                    TEST_PASSWORD,
                    faker.phoneNumber().phoneNumber(),
                    faker.address().fullAddress()
                );
                client.post("/residents", JSON_MAPPER.writeValueAsString(resident));
            }
            
            var response = client.get("/residents");
            assertEquals(200, response.code());
            
            String body = response.body().string();
            assertTrue(body.contains("\"email\""));
            assertTrue(body.contains("\"address\""));
            assertTrue(body.contains("\"dateOfBirth\""));
            assertFalse(body.contains("\"password\""));
        });
    }
}