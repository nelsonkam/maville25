package api.controllers;

import api.services.ConstructionWorkService;
import io.javalin.http.Context;
import api.MontrealApiClient;
import models.ConstructionWork;
import java.util.List;
import java.util.stream.Collectors;

public class ConstructionWorkController {
    private final ConstructionWorkService constructionWorkService;

    public ConstructionWorkController(ConstructionWorkService constructionWorkService) {
        this.constructionWorkService = constructionWorkService;
    }

    public void getCurrentWorks(Context ctx) {
        try {
            String borough = ctx.queryParam("borough");
            String type = ctx.queryParam("type");
            
            List<ConstructionWork> works = constructionWorkService.getCurrentWorks(borough, type);
            ctx.json(works);
        } catch (Exception e) {
            ctx.status(500).result("Error fetching construction works: " + e.getMessage());
        }
    }
}
