package api.controllers;

import org.junit.jupiter.api.Test;
import models.Project;
import org.junit.jupiter.api.TestInstance;
import io.javalin.testtools.JavalinTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 *  Cette classe teste la récupération de tous les projets
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetAllProjectsTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Teste la récupération de tous les projets
     */
    @Test
    public void testGetAllProjects() {
        JavalinTest.test(createTestApp(), (server, client) -> {

            Project project1 = new Project(
                "Fontaine Lac aux castor",
                "Installation d'une fontaine au milieu du Lac des castors", 
                "Le Plateau-Mont-Royal", 
                LocalDate.parse("2025-01-01")
            ); 

            Project project2 = new Project(
                "Zone éponge",
                "Réaménagement d'un lot vacant pour le transformer en zone éponge en cas de forte crue des eaux", 
                "Verdun", 
                LocalDate.parse("2025-02-02")
            );

            Project project3 = new Project(
                "Parc des Gorilles",
                "Réaménagement du lot en face de l'Excellent Garage pour en faire un parc", 
                "Rosemont-La-Petite-Patrie", 
                LocalDate.parse("2025-03-03")
            );

            String workRequestJson = JSON_MAPPER.writeValueAsString(project1);
            client.post("/work-requests", workRequestJson);

            workRequestJson = JSON_MAPPER.writeValueAsString(project2);
            client.post("/work-requests", workRequestJson);

            workRequestJson = JSON_MAPPER.writeValueAsString(project3);
            client.post("/work-requests", workRequestJson);

            // Récupération de tous les projets
            var response = client.get("/projects");
            assertEquals(200, response.code());            
        });
    }
}
