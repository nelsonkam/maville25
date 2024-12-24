package api.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import models.Project;
import io.javalin.testtools.JavalinTest;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Cette classe teste la soumission d'un projet
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubmitProjectTest extends BaseControllerTest {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Tests la soumission d'un projet
     */
    @Test
    public void testSubmitProject() {
        JavalinTest.test(createTestApp(), (server, client) -> {

            //Création d'un projet fictif
            Project project = new Project(
                "Mini-Tour Eiffel",
                "Construction d'une mini Tour Eiffel au milieu du lac du parc Lafontaine", 
                "Le Plateau-Mont-Royal", 
                LocalDate.parse("2025-05-05")
            ); 

            String projectJson = JSON_MAPPER.writeValueAsString(project);
            client.post("/projects", projectJson);

            var response = client.post("/projects", projectJson);
            assertEquals(201, response.code());
        });
    }
}
