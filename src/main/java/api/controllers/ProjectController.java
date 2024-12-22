package api.controllers;

import api.services.ProjectService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Project;

import java.time.LocalDate;

/**
 * Cette classe gère les requêtes HTTP pour les projets.
 */

 public class ProjectController {

    private final ProjectService projectService;
    private final ObjectMapper jsonMapper;

    /**
     * Constructeur de la classe WorkRequestController.
     *
     * @param workRequestService Le service de gestion des demandes de travaux.
     * @param jsonMapper Le mapper JSON pour la sérialisation/désérialisation.
     */
    public ProjectController(ProjectService projectService, ObjectMapper jsonMapper) {
        this.projectService = projectService;
        this.jsonMapper = jsonMapper;
    }

    /**
     * Soumet un projet.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void submitProject(Context ctx) {
        try {
            Project project = jsonMapper.readValue(ctx.body(), Project.class);
            
            // Valide la demande de projet
            if (project.getDesiredStartDate().isBefore(LocalDate.now())) {
                ctx.status(400).result("Date must be in the future");
                return;
            }
            try {
                projectService.submitProject(project);
                ctx.status(201).json(project);
            } catch (IllegalArgumentException e) {
                ctx.status(400).result(e.getMessage());
            }
        } catch (Exception e) {
            ctx.status(500).result("Failed to submit project: " + e.getMessage());
        }
    }    


    /**
     * Récupère toutes les demandes de travaux.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void getAllProjects(Context ctx) {
        try {
            var projects = projectService.getAllProjects();
            ctx.json(projects);
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch projects: " + e.getMessage());
        }
    }
}
