package api.controllers;

import api.services.WorkPreferenceService;
import io.javalin.http.Context;
import models.WorkPreference;

import java.util.List;

/**
 * Contrôleur pour gérer les endpoints liés aux préférences horaires.
 */
public class WorkPreferenceController {
    private final WorkPreferenceService service;

    /**
     * Constructeur.
     *
     * @param service Le service métier associé.
     */
    public WorkPreferenceController(WorkPreferenceService service) {
        this.service = service;
    }

    /**
     * Endpoint pour sauvegarder une nouvelle préférence horaire.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void savePreference(Context ctx) {
        try {
            WorkPreference preference = ctx.bodyAsClass(WorkPreference.class);
            service.savePreference(preference);
            ctx.status(201).result("Preference saved successfully");
        } catch (Exception e) {
            ctx.status(500).result("Failed to save preference: " + e.getMessage());
        }
    }

    /**
     * Endpoint pour mettre à jour une préférence horaire existante.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void updatePreference(Context ctx) {
        try {
            WorkPreference preference = ctx.bodyAsClass(WorkPreference.class);
            service.updatePreference(preference);
            ctx.status(200).result("Preference updated successfully");
        } catch (Exception e) {
            ctx.status(500).result("Failed to update preference: " + e.getMessage());
        }
    }

    /**
     * Endpoint pour récupérer les préférences horaires d'un résident.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void getPreferencesByResident(Context ctx) {
        try {
            String email = ctx.queryParam("email");
            List<WorkPreference> preferences = service.getPreferencesByResident(email);
            ctx.json(preferences);
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch preferences: " + e.getMessage());
        }
    }
}
