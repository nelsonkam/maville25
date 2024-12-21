package api.controllers;

import api.DatabaseManager;
import api.repositories.ResidentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    private static final String TEST_EMAIL = "test.resident@example.com";
    private static final String TEST_PASSWORD = "password123";
    @BeforeEach
     void createTestResident() throws Exception {
        Resident resident = new Resident(
            "Test Resident",
            LocalDate.now().minusYears(25),
            TEST_EMAIL,
            TEST_PASSWORD,
            "514-555-0123",
            "123 Test Street"
        );
        new ResidentRepository(DatabaseManager.getInstance()).save(resident);
    }

    /**
     * Teste l'envoi d'une notification à un résident.
     */
    @Test
    public void testSendNotification() {
        JavalinTest.test(createTestApp(), (server, client) -> {

            String notificationJson = """
                {
                    "residentEmail": "%s",
                    "message": "Test notification message"
                }
                """.formatted(TEST_EMAIL);

            var response = client.post("/notifications", notificationJson);
            assertEquals(201, response.code());
        });
    }

    /**
     * Teste la récupération des notifications non lues.
     */
    @Test
    public void testGetUnreadNotifications() {
        JavalinTest.test(createTestApp(), (server, client) -> {

            // Envoyer quelques notifications
            for (int i = 1; i <= 3; i++) {
                String notificationJson = """
                    {
                        "residentEmail": "%s",
                        "message": "Test notification %d"
                    }
                    """.formatted(TEST_EMAIL, i);
                client.post("/notifications", notificationJson);
            }

            // Récupérer les notifications non lues
            var response = client.get("/notifications/unread/" + TEST_EMAIL);
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
        JavalinTest.test(createTestApp(), (server, client) -> {

            // Envoyer une notification
            String notificationJson = """
                {
                    "residentEmail": "%s",
                    "message": "Test notification"
                }
                """.formatted(TEST_EMAIL);
            client.post("/notifications", notificationJson);

            // Marquer comme lue
            var markResponse = client.post("/notifications/" + TEST_EMAIL + "/mark-read", "");
            assertEquals(200, markResponse.code());

            // Vérifier qu'il n'y a plus de notifications non lues
            var unreadResponse = client.get("/notifications/unread/" + TEST_EMAIL);
            assertEquals(200, unreadResponse.code());
            assertEquals("[]", unreadResponse.body().string());
        });
    }

    /**
     * Teste la récupération de toutes les notifications d'un résident.
     */
    @Test
    public void testGetAllNotifications() {
        JavalinTest.test(createTestApp(), (server, client) -> {

            // Envoyer quelques notifications
            for (int i = 1; i <= 3; i++) {
                String notificationJson = """
                    {
                        "residentEmail": "%s",
                        "message": "Test notification %d"
                    }
                    """.formatted(TEST_EMAIL, i);
                client.post("/notifications", notificationJson);
            }

            // Marquer certaines comme lues
            client.post("/notifications/" + TEST_EMAIL + "/mark-read", "");

            // Récupérer toutes les notifications
            var response = client.get("/notifications/" + TEST_EMAIL);
            assertEquals(200, response.code());
            
            String responseBody = response.body().string();
            assertTrue(responseBody.contains("Test notification 1"));
            assertTrue(responseBody.contains("Test notification 2"));
            assertTrue(responseBody.contains("Test notification 3"));
        });
    }
}
