package cli;

import api.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    private static final String API_BASE_URL = "http://localhost:7000";
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private final HttpClient httpClient;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void registerResident(Resident resident) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/residents"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(resident)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new RuntimeException("Registration failed: " + response.body());
        }
    }

    public void registerIntervenant(Intervenant intervenant) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/intervenants"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(intervenant)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new RuntimeException("Registration failed: " + response.body());
        }
    }

    public Resident loginResident(String email, String password) throws IOException, InterruptedException {
        LoginRequest loginRequest = new LoginRequest(email, password);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/residents/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(loginRequest)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Login failed: " + response.body());
        }
        return JSON_MAPPER.readValue(response.body(), Resident.class);
    }

    public void submitWorkRequest(WorkRequest request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/work-requests"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(request)))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new RuntimeException("Failed to submit work request: " + response.body());
        }
    }

    public List<WorkRequest> getResidentWorkRequests(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/work-requests/resident/" + email))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch work requests: " + response.body());
        }

        return JSON_MAPPER.readValue(
            response.body(),
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, WorkRequest.class)
        );
    }

    public List<WorkRequest> getAllWorkRequests() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/work-requests"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch work requests: " + response.body());
        }

        return JSON_MAPPER.readValue(
            response.body(),
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, WorkRequest.class)
        );
    }

    public List<RoadImpact> getRoadImpacts(String workId, String streetName) throws IOException, InterruptedException {
        StringBuilder url = new StringBuilder(API_BASE_URL + "/road-impacts");
        
        if (workId != null || streetName != null) {
            url.append("?");
            if (workId != null) {
                url.append("workId=").append(workId);
            }
            if (streetName != null) {
                if (workId != null) url.append("&");
                url.append("streetName=").append(streetName);
            }
        }
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch road impacts: " + response.body());
        }
        
        return JSON_MAPPER.readValue(
            response.body(), 
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, RoadImpact.class)
        );
    }

    public List<ConstructionWork> getCurrentWorks(String borough, String type) throws IOException, InterruptedException {
        StringBuilder url = new StringBuilder(API_BASE_URL + "/construction-works");
        
        if (borough != null || type != null) {
            url.append("?");
            if (borough != null) {
                url.append("borough=").append(borough);
            }
            if (type != null) {
                if (borough != null) url.append("&");
                url.append("type=").append(type);
            }
        }
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch construction works: " + response.body());
        }
        
        return JSON_MAPPER.readValue(
            response.body(), 
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, ConstructionWork.class)
        );
    }

    public Intervenant loginIntervenant(String email, String password) throws IOException, InterruptedException {
        LoginRequest loginRequest = new LoginRequest(email, password);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/intervenants/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(loginRequest)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Login failed: " + response.body());
        }
        return JSON_MAPPER.readValue(response.body(), Intervenant.class);
    }
}
