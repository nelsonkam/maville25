package api.controllers;

import api.services.WorkRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.WorkRequest;

import java.time.LocalDate;

/**
 * Cette classe gère les requêtes HTTP pour les demandes de travaux.
 */
public class WorkRequestController {
    private final WorkRequestService workRequestService;
    private final ObjectMapper jsonMapper;

    /**
     * Constructeur de la classe WorkRequestController.
     *
     * @param workRequestService Le service de gestion des demandes de travaux.
     * @param jsonMapper Le mapper JSON pour la sérialisation/désérialisation.
     */
    public WorkRequestController(WorkRequestService workRequestService, ObjectMapper jsonMapper) {
        this.workRequestService = workRequestService;
        this.jsonMapper = jsonMapper;
    }

    /**
     * Soumet une demande de travaux.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void submitRequest(Context ctx) {
        try {
            WorkRequest request = jsonMapper.readValue(ctx.body(), WorkRequest.class);
            
            // Valide la demande de travaux
            if (request.getDesiredStartDate().isBefore(LocalDate.now())) {
                ctx.status(400).result("Date must be in the future");
                return;
            }

            // Valide le type de travaux
            if (!isValidWorkType(request.getWorkType())) {
                ctx.status(400).result("Invalid work type");
                return;
            }

            try {
                workRequestService.submitRequest(request);
                ctx.status(201).json(request);
            } catch (IllegalArgumentException e) {
                ctx.status(400).result(e.getMessage());
            }
        } catch (Exception e) {
            ctx.status(500).result("Failed to submit work request: " + e.getMessage());
        }
    }

    /**
     * Récupère les demandes de travaux d'un résident.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void getResidentRequests(Context ctx) {
        try {
            String email = ctx.pathParam("email");
            try {
                var requests = workRequestService.getResidentRequests(email);
                ctx.json(requests);
            } catch (IllegalArgumentException e) {
                ctx.status(404).result("Resident not found");
            }
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch resident's work requests: " + e.getMessage());
        }
    }

    /**
     * Récupère toutes les demandes de travaux.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void getAllRequests(Context ctx) {
        try {
            var requests = workRequestService.getAllWorkRequests();
            ctx.json(requests);
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch work requests: " + e.getMessage());
        }
    }

    /**
     * Vérifie si le type de travaux est valide.
     *
     * @param workType Le type de travaux à vérifier.
     * @return true si le type de travaux est valide, false sinon.
     */
    private boolean isValidWorkType(String workType) {
        return workType != null && (
            workType.equals("Voirie") ||
            workType.equals("Aqueduc") ||
            workType.equals("Égouts") ||
            workType.equals("Éclairage public") ||
            workType.equals("Signalisation") ||
            workType.equals("Autre")
        );
    }
}