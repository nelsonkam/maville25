package api.controllers;

import api.services.RoadImpactService;
import io.javalin.http.Context;
import models.RoadImpact;

import java.util.List;

public class RoadImpactController {
    private final RoadImpactService roadImpactService;

    public RoadImpactController(RoadImpactService roadImpactService) {
        this.roadImpactService = roadImpactService;
    }

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
