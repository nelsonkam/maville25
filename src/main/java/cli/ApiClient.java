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

/**
 * Cette classe gère les appels API pour l'application MaVille.
 */
public class ApiClient {
    private static final String API_BASE_URL = "http://localhost:9090";
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private final HttpClient httpClient;

    /**
     * Constructeur de la classe ApiClient.
     * Initialise le client HTTP.
     */
    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Enregistre un résident via l'API.
     *
     * @param resident Le résident à enregistrer.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Enregistre un intervenant via l'API.
     *
     * @param intervenant L'intervenant à enregistrer.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Authentifie un résident via l'API.
     *
     * @param email L'email du résident.
     * @param password Le mot de passe du résident.
     * @return Le résident authentifié.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Soumet une demande de travaux via l'API.
     *
     * @param request La demande de travaux à soumettre.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Récupère les demandes de travaux d'un résident via l'API.
     *
     * @param email L'email du résident.
     * @return La liste des demandes de travaux du résident.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Récupère toutes les demandes de travaux via l'API.
     *
     * @return La liste de toutes les demandes de travaux.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    public List<Notification> getUnreadNotifications(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/notifications/unread/" + email))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch unread notifications: " + response.body());
        }

        return JSON_MAPPER.readValue(
            response.body(),
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, Notification.class)
        );
    }

    public List<Notification> getAllNotifications(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/notifications/" + email))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch notifications: " + response.body());
        }

        return JSON_MAPPER.readValue(
            response.body(),
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, Notification.class)
        );
    }

    public void markNotificationsAsRead(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/notifications/" + email + "/mark-read"))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to mark notifications as read: " + response.body());
        }
    }

    /**
     * Récupère les entraves routières via l'API.
     *
     * @param workId L'ID du travail à filtrer (peut être null).
     * @param streetName Le nom de la rue à filtrer (peut être null).
     * @return La liste des entraves routières.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Récupère les travaux en cours via l'API.
     *
     * @param borough L'arrondissement à filtrer (peut être null).
     * @param type Le type de travaux à filtrer (peut être null).
     * @return La liste des travaux en cours.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Authentifie un intervenant via l'API.
     *
     * @param email L'email de l'intervenant.
     * @param password Le mot de passe de l'intervenant.
     * @return L'intervenant authentifié.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
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

    /**
     * Soumet un projet via l'API.
     *
     * @param project La demande de travaux à soumettre.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
    public void submitProject(Project project) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/projects"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(project)))
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 201) {
            throw new RuntimeException("Failed to submit project: " + response.body());
        }
    }

    /**
     * Récupère tous les projets via l'API.
     *
     * @return La liste de toutes les demandes de travaux.
     * @throws IOException Si une erreur d'entrée/sortie survient.
     * @throws InterruptedException Si l'opération est interrompue.
     */
    public List<Project> getAllProjects() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_BASE_URL + "/projects"))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch projects: " + response.body());
        }

        return JSON_MAPPER.readValue(
            response.body(),
            JSON_MAPPER.getTypeFactory().constructCollectionType(List.class, Project.class)
        );
    }
}
