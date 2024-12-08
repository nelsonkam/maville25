package api.controllers;

import api.services.RoadImpactService;
import io.javalin.http.Context;
import models.RoadImpact;

import java.util.List;

/**
 * Cette classe gère les requêtes HTTP pour les impacts routiers.
 */
public class RoadImpactController {
    private final RoadImpactService roadImpactService;

    /**
     * Constructeur de la classe RoadImpactController.
     *
     * @param roadImpactService Le service de gestion des impacts routiers.
     */
    public RoadImpactController(RoadImpactService roadImpactService) {
        this.roadImpactService = roadImpactService;
    }

    /**
     * Récupère les impacts routiers.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void getRoadImpacts(Context ctx) {
        try {
            String workId = ctx.queryParam("workId");
            String streetName = ctx.queryParam("streetName");
            
            List<RoadImpact> impacts = roadImpactService.getRoadImpacts(workId, streetName);
            ctx.json(impacts);
        } catch (Exception e) {
            ctx.status(500).result("Error fetching road impacts: " + e.getMessage());
        }
    }
}