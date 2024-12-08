package api.controllers;

import api.services.ConstructionWorkService;
import io.javalin.http.Context;
import models.ConstructionWork;

import java.util.List;

/**
 * Cette classe gère les requêtes HTTP pour les travaux de construction.
 */
public class ConstructionWorkController {
    private final ConstructionWorkService constructionWorkService;

    /**
     * Constructeur de la classe ConstructionWorkController.
     *
     * @param constructionWorkService Le service de gestion des travaux de construction.
     */
    public ConstructionWorkController(ConstructionWorkService constructionWorkService) {
        this.constructionWorkService = constructionWorkService;
    }

    /**
     * Récupère les travaux de construction en cours.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
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