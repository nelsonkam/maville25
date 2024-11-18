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

public class MontrealApiClient {
    private static final String API_URL = "https://donnees.montreal.ca/api/3/action/datastore_search";
    private static final String WORKS_RESOURCE_ID = "cc41b532-f12d-40fb-9f55-eb58c9a2b12b";
    private static final String IMPACTS_RESOURCE_ID = "a2bc8014-488c-495d-941b-e7ae1999d1bd";
    private final HttpClient httpClient;
    private final ObjectMapper jsonMapper;

    public MontrealApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.jsonMapper = new ObjectMapper();
    }

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
