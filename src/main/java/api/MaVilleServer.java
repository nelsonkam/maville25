package api;

import api.controllers.*;
import api.repositories.*;
import api.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;

public class MaVilleServer {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
        .registerModule(new JavaTimeModule());
    private static DatabaseManager db = DatabaseManager.getInstance();

    public static Javalin createApp() {
        ResidentRepository residentRepo = new ResidentRepository(db);
        IntervenantRepository intervenantRepo = new IntervenantRepository(db);

        ResidentService residentService = new ResidentService(residentRepo);
        IntervenantService intervenantService = new IntervenantService(intervenantRepo);
        ConstructionWorkService constructionWorkService = new ConstructionWorkService();
        RoadImpactService roadImpactService = new RoadImpactService();

        ResidentController residentController = new ResidentController(residentService, JSON_MAPPER);
        IntervenantController intervenantController = new IntervenantController(intervenantService, JSON_MAPPER);

        ConstructionWorkController constructionWorkController = new ConstructionWorkController(constructionWorkService);
        RoadImpactController roadImpactController = new RoadImpactController(roadImpactService);

        Javalin app = Javalin.create();

        app.post("/residents", residentController::register);
        app.post("/residents/login", residentController::login);
        app.get("/residents", residentController::getAllResidents);

        app.post("/intervenants", intervenantController::register);
        app.post("/intervenants/login", intervenantController::login);
        app.get("/intervenants", intervenantController::getAllIntervenants);

        app.get("/construction-works", constructionWorkController::getCurrentWorks);
        app.get("/road-impacts", roadImpactController::getRoadImpacts);

        WorkRequestRepository workRequestRepo = new WorkRequestRepository(db);
        WorkRequestService workRequestService = new WorkRequestService(workRequestRepo);
        WorkRequestController workRequestController = new WorkRequestController(workRequestService, JSON_MAPPER);

        app.post("/work-requests", workRequestController::submitRequest);
        app.get("/work-requests/resident/{email}", workRequestController::getResidentRequests);
        app.get("/work-requests", workRequestController::getAllRequests);

        return app;
    }

    public static void main(String[] args) {
        Javalin app = createApp();
        app.start(7000);
        System.out.println("Server started on port 7000");
    }

}
