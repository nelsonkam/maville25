package api;

import api.controllers.*;
import api.repositories.CandidatureRepository;
import api.repositories.IntervenantRepository;
import api.repositories.ResidentRepository;
import api.repositories.WorkRequestRepository;
import api.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;

public class MaVilleServer {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    private static DatabaseManager db = DatabaseManager.getInstance();

    public static Javalin createApp() {
        // Repositories
        ResidentRepository residentRepo = new ResidentRepository(db);
        IntervenantRepository intervenantRepo = new IntervenantRepository(db);
        WorkRequestRepository workRequestRepo = new WorkRequestRepository(db);
        CandidatureRepository candidatureRepo = new CandidatureRepository(db);

        // Services
        ResidentService residentService = new ResidentService(residentRepo);
        IntervenantService intervenantService = new IntervenantService(intervenantRepo);
        ConstructionWorkService constructionWorkService = new ConstructionWorkService();
        RoadImpactService roadImpactService = new RoadImpactService();
        WorkRequestService workRequestService = new WorkRequestService(workRequestRepo);
        CandidatureService candidatureService = new CandidatureService(candidatureRepo, workRequestRepo, intervenantRepo);

        // Controllers
        ResidentController residentController = new ResidentController(residentService, JSON_MAPPER);
        IntervenantController intervenantController = new IntervenantController(intervenantService, JSON_MAPPER);
        ConstructionWorkController constructionWorkController = new ConstructionWorkController(constructionWorkService);
        RoadImpactController roadImpactController = new RoadImpactController(roadImpactService);
        WorkRequestController workRequestController = new WorkRequestController(workRequestService, JSON_MAPPER);
        CandidatureController candidatureController = new CandidatureController(candidatureService, JSON_MAPPER);

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

        // Candidatures (mise à jour avec la syntaxe {id} et {candidatureId} pour Javalin 4)
        app.post("/work-requests/{id}/candidatures", candidatureController::submitCandidature);
        app.delete("/work-requests/{id}/candidatures", candidatureController::withdrawCandidature);
        app.get("/work-requests/{id}/candidatures", candidatureController::getCandidatures);
        app.post("/work-requests/{id}/candidatures/{candidatureId}/select", candidatureController::selectCandidature);
        app.post("/work-requests/{id}/candidatures/{candidatureId}/confirm", candidatureController::confirmCandidature);

        return app;
    }

    public static void main(String[] args) {
        DatabaseManager db = DatabaseManager.getInstance();

        DataInitializer initializer = new DataInitializer(db);
        try {
            initializer.initializeData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Javalin app = createApp();
        app.start(7000);
        System.out.println("Server started on port 7000");
    }
}
