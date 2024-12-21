package api.controllers;

import api.DatabaseManager;
import api.repositories.ResidentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import io.javalin.testtools.JavalinTest;
import models.Notification;
import models.Resident;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Cette classe teste les fonctionnalités du système de notification.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationControllerTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private static final Faker faker = new Faker();


    Resident createTestResident()  {
        Resident resident = new Resident(
            "Test Resident",
            LocalDate.now().minusYears(25),
            faker.internet().emailAddress(),
                "password123",
            "514-555-0123",
            "123 Test Street"
        );
        new ResidentRepository(DatabaseManager.getInstance()).save(resident);
        return resident;
    }

    /**
     * Teste l'envoi d'une notification à un résident.
     */
    @Test
    public void testSendNotification() {
        Resident resident = createTestResident();
        JavalinTest.test(createTestApp(), (server, client) -> {

            String notificationJson = """
                {
                    "residentEmail": "%s",
                    "message": "Test notification message"
                }
                """.formatted(resident.getEmail());

            var response = client.post("/notifications", notificationJson);
            assertEquals(201, response.code());
        });
    }

    /**
     * Teste la récupération des notifications non lues.
     */
    @Test
    public void testGetUnreadNotifications() {
        Resident resident = createTestResident();
        JavalinTest.test(createTestApp(), (server, client) -> {

            // Envoyer quelques notifications
            for (int i = 1; i <= 3; i++) {
                String notificationJson = """
                    {
                        "residentEmail": "%s",
                        "message": "Test notification %d"
                    }
                    """.formatted(resident.getEmail(), i);
                client.post("/notifications", notificationJson);
            }

            // Récupérer les notifications non lues
            var response = client.get("/notifications/unread/" + resident.getEmail());
            assertEquals(200, response.code());
            
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("Test notification 1"));
            assertTrue(responseBody.contains("Test notification 2"));
            assertTrue(responseBody.contains("Test notification 3"));
        });
    }

    /**
     * Teste le marquage des notifications comme lues.
     */
    @Test
    public void testMarkNotificationsAsRead() {
        Resident resident = createTestResident();
        JavalinTest.test(createTestApp(), (server, client) -> {

            // Envoyer une notification
            String notificationJson = """
                {
                    "residentEmail": "%s",
                    "message": "Test notification"
                }
                """.formatted(resident.getEmail());
            client.post("/notifications", notificationJson);

            // Marquer comme lue
            var markResponse = client.post("/notifications/" + resident.getEmail() + "/mark-read", "");
            assertEquals(200, markResponse.code());

            // Vérifier qu'il n'y a plus de notifications non lues
            var unreadResponse = client.get("/notifications/unread/" + resident.getEmail());
            assertEquals(200, unreadResponse.code());
            assertEquals("[]", unreadResponse.body().string());
        });
    }

    /**
     * Teste la récupération de toutes les notifications d'un résident.
     */
    @Test
    public void testGetAllNotifications() {
        Resident resident = createTestResident();
        JavalinTest.test(createTestApp(), (server, client) -> {

            // Envoyer quelques notifications
            for (int i = 1; i <= 3; i++) {
                String notificationJson = """
                    {
                        "residentEmail": "%s",
                        "message": "Test notification %d"
                    }
                    """.formatted(resident.getEmail(), i);
                client.post("/notifications", notificationJson);
            }

            // Marquer certaines comme lues
            client.post("/notifications/" + resident.getEmail() + "/mark-read", "");

            // Récupérer toutes les notifications
            var response = client.get("/notifications/" + resident.getEmail());
            assertEquals(200, response.code());
            
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("Test notification 1"));
            assertTrue(responseBody.contains("Test notification 2"));
            assertTrue(responseBody.contains("Test notification 3"));
        });
    }
}
