package api;

import api.controllers.*;
import api.repositories.*;
import api.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;

/**
 * Cette classe configure et démarre le serveur Javalin pour l'application MaVille.
 */
public class MaVilleServer {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    private static DatabaseManager db = DatabaseManager.getInstance();

    /**
     * Crée et configure l'application Javalin.
     *
     * @return L'application Javalin configurée.
     */
    public static Javalin createApp() {
        // Repositories
        ResidentRepository residentRepo = new ResidentRepository(db);
        IntervenantRepository intervenantRepo = new IntervenantRepository(db);
        WorkRequestRepository workRequestRepo = new WorkRequestRepository(db);
        CandidatureRepository candidatureRepo = new CandidatureRepository(db);
        ProjectRepository projectRepo = new ProjectRepository(db);

        // Services
        ResidentService residentService = new ResidentService(residentRepo);
        IntervenantService intervenantService = new IntervenantService(intervenantRepo);
        ConstructionWorkService constructionWorkService = new ConstructionWorkService();
        RoadImpactService roadImpactService = new RoadImpactService();
        WorkRequestService workRequestService = new WorkRequestService(workRequestRepo);
        CandidatureService candidatureService = new CandidatureService(candidatureRepo, workRequestRepo, intervenantRepo);
        NotificationService notificationService = new NotificationService(new NotificationRepository(db));
        ProjectService projectService = new ProjectService(projectRepo);

        // Controllers
        ResidentController residentController = new ResidentController(residentService, JSON_MAPPER);
        IntervenantController intervenantController = new IntervenantController(intervenantService, JSON_MAPPER);
        ConstructionWorkController constructionWorkController = new ConstructionWorkController(constructionWorkService);
        RoadImpactController roadImpactController = new RoadImpactController(roadImpactService);
        WorkRequestController workRequestController = new WorkRequestController(workRequestService, JSON_MAPPER);
        CandidatureController candidatureController = new CandidatureController(candidatureService, JSON_MAPPER);
        NotificationController notificationController = new NotificationController(notificationService, JSON_MAPPER);
        ProjectController projectController = new ProjectController(projectService, JSON_MAPPER);

        // Javalin app
        Javalin app = Javalin.create();

        // Routes existantes
        app.post("/residents", residentController::register);
        app.post("/residents/login", residentController::login);
        app.get("/residents", residentController::getAllResidents);

        app.post("/intervenants", intervenantController::register);
        app.post("/intervenants/login", intervenantController::login);
        app.get("/intervenants", intervenantController::getAllIntervenants);

        app.get("/construction-works", constructionWorkController::getCurrentWorks);
        app.get("/road-impacts", roadImpactController::getRoadImpacts);

        // Work Requests
        app.post("/work-requests", workRequestController::submitRequest);
        app.get("/work-requests/resident/{email}", workRequestController::getResidentRequests);
        app.get("/work-requests", workRequestController::getAllRequests);

        // Candidatures
        app.post("/work-requests/{id}/candidatures", candidatureController::submitCandidature);
        app.delete("/work-requests/{id}/candidatures", candidatureController::withdrawCandidature);
        app.get("/work-requests/{id}/candidatures", candidatureController::getCandidatures);
        app.post("/work-requests/{id}/candidatures/{candidatureId}/select", candidatureController::selectCandidature);
        app.post("/work-requests/{id}/candidatures/{candidatureId}/confirm", candidatureController::confirmCandidature);

        // Notifications
        app.post("/notifications", notificationController::sendNotification);
        app.get("/notifications/unread/{email}", notificationController::getUnreadNotifications);
        app.get("/notifications/{email}", notificationController::getAllNotifications);
        app.post("/notifications/{email}/mark-read", notificationController::markAsRead);

        //Projets
        app.post("/projects", projectController::submitProject);
        app.get("/projects", projectController::getAllProjects);

        return app;
    }

    /**
     * Point d'entrée principal de l'application MaVilleServer.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();

        DataInitializer initializer = new DataInitializer(db);
        try {
            initializer.initializeData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Javalin app = createApp();
        app.start(9090);
        System.out.println("Server started on port 9090");
    }
}
