package api.controllers;

import io.javalin.testtools.JavalinTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Cette classe teste les requêtes HTTP pour les impacts routiers.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoadImpactControllerTest extends BaseControllerTest {

    /**
     * Teste la récupération de tous les impacts routiers.
     */
    @Test
    public void testAllRoadImpact() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            // Test get all road-impacts
            var allRoadImpactResponse = client.get("/road-impacts");
            assertEquals(200, allRoadImpactResponse.code());
        });
    }

    /**
     * Teste la récupération des impacts routiers par nom de rue.
     */
    @Test
    public void testRoadImpactByStreet() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            // Test filter road impacts on rue Garnier
            var RoadImpactByStreetResponse = client.get("/road-impacts?streetName=Garnier");
            assertEquals(200, RoadImpactByStreetResponse.code());
        });
    }

    /**
     * Teste la récupération des impacts routiers par identifiant de demande.
     */
    @Test
    public void testRoadImpactById() {
        JavalinTest.test(createTestApp(), (server, client) -> {
            // Test filter road impact by id
            var RoadImpactByID = client.get("/road-impacts?requestID=65b40710675f6600194688fe");
            assertEquals(200, RoadImpactByID.code());
        });
    }
}