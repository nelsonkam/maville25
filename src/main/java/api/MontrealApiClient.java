package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.ConstructionWork;
import models.RoadImpact;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe gère les appels API à l'API de données ouvertes de Montréal pour récupérer des informations sur les travaux de construction et les impacts routiers.
 */
public class MontrealApiClient {
    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search";
    private static final String WORKS_RESOURCE_ID = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    private static final String IMPACTS_RESOURCE_ID = "a2bc8014-488c-495d-941b-e7ae1999d1bd";
    private final HttpClient httpClient;
    private final ObjectMapper jsonMapper;

    /**
     * Constructeur de la classe MontrealApiClient.
     * Initialise le client HTTP et le mapper JSON.
     */
    public MontrealApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.jsonMapper = new ObjectMapper();
    }

    /**
     * Récupère les travaux de construction en cours via l'API de Montréal.
     *
     * @return Une liste de travaux de construction.
     * @throws Exception Si une erreur survient lors de la récupération des données.
     */
    public List<ConstructionWork> getCurrentWorks() throws Exception {
        String url = API_URL + "?resource_id=" + WORKS_RESOURCE_ID;
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("API request failed with status: " + response.statusCode());
        }

        JsonNode root = jsonMapper.readTree(response.body());
        JsonNode records = root.path("result").path("records");
        
        List<ConstructionWork> works = new ArrayList<>();
        for (JsonNode record : records) {
            works.add(new ConstructionWork(
                record.path("id").asText(),
                record.path("boroughid").asText(),
                record.path("currentstatus").asText(),
                record.path("reason_category").asText(),
                record.path("submittercategory").asText(),
                record.path("organizationname").asText()
            ));
        }
        
        return works;
    }

    /**
     * Récupère les impacts routiers via l'API de Montréal.
     *
     * @param workId L'identifiant des travaux à filtrer (peut être null).
     * @param streetName Le nom de la rue à filtrer (peut être null).
     * @return Une liste d'impacts routiers.
     * @throws Exception Si une erreur survient lors de la récupération des données.
     */
    public List<RoadImpact> getRoadImpacts(String workId, String streetName) throws Exception {
        StringBuilder url = new StringBuilder(API_URL + "?resource_id=" + IMPACTS_RESOURCE_ID);
        
        if (workId != null) {
            url.append("&q=").append(workId);
        }
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url.toString()))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("API request failed with status: " + response.statusCode());
        }

        JsonNode root = jsonMapper.readTree(response.body());
        JsonNode records = root.path("result").path("records");
        
        List<RoadImpact> impacts = new ArrayList<>();
        for (JsonNode record : records) {
            RoadImpact impact = new RoadImpact(
                record.path("id_request").asText(),
                record.path("streetid").asText(),
                record.path("shortname").asText(),
                record.path("streetimpacttype").asText()
            );
            
            if (streetName == null || 
                impact.getStreetName().toLowerCase().contains(streetName.toLowerCase())) {
                impacts.add(impact);
            }
        }
        
        return impacts;
    }
}