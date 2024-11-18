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
        // Initialize repositories
        ResidentRepository residentRepo = new ResidentRepository(db);
        IntervenantRepository intervenantRepo = new IntervenantRepository(db);

        // Initialize services
        ResidentService residentService = new ResidentService(residentRepo);
        IntervenantService intervenantService = new IntervenantService(intervenantRepo);

        // Initialize controllers
        ResidentController residentController = new ResidentController(residentService, JSON_MAPPER);
        IntervenantController intervenantController = new IntervenantController(intervenantService, JSON_MAPPER);


        // Create and configure Javalin app
        Javalin app = Javalin.create();

        // Configure routes
        app.post("/residents", residentController::register);
        app.post("/residents/login", residentController::login);
        app.get("/residents", residentController::getAllResidents);

        app.post("/intervenants", intervenantController::register);
        app.post("/intervenants/login", intervenantController::login);
        app.get("/intervenants", intervenantController::getAllIntervenants);
        


        return app;
    }

    public static void main(String[] args) {
        Javalin app = createApp();
        app.start(7000);
        System.out.println("Server started on port 7000");
    }

}
