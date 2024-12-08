package api.controllers;

import api.services.CandidatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Candidature;

import java.util.Map;

/**
 * Cette classe gère les requêtes HTTP pour les candidatures.
 */
public class CandidatureController {
    private final CandidatureService candidatureService;
    private final ObjectMapper jsonMapper;

    /**
     * Constructeur de la classe CandidatureController.
     *
     * @param candidatureService Le service de gestion des candidatures.
     * @param jsonMapper Le mapper JSON pour la sérialisation/désérialisation.
     */
    public CandidatureController(CandidatureService candidatureService, ObjectMapper jsonMapper) {
        this.candidatureService = candidatureService;
        this.jsonMapper = jsonMapper;
    }

    /**
     * Soumet une candidature.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void submitCandidature(Context ctx) {
        Long workRequestId = Long.parseLong(ctx.pathParam("id"));
        try {
            Map<String, Object> body = jsonMapper.readValue(ctx.body(), Map.class);
            String intervenantEmail = (String) body.get("intervenantEmail");

            if (intervenantEmail == null || intervenantEmail.trim().isEmpty()) {
                ctx.status(400).result("l'email de l'intervenant est requis");
                return;
            }

            Candidature candidature = candidatureService.submitCandidature(workRequestId, intervenantEmail);
            ctx.status(201).json(candidature);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erreur : " + e.getMessage());
        }
    }

    /**
     * Retire une candidature.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void withdrawCandidature(Context ctx) {
        Long workRequestId = Long.parseLong(ctx.pathParam("id"));
        String intervenantEmail = ctx.queryParam("intervenantEmail");

        if (intervenantEmail == null || intervenantEmail.isEmpty()) {
            ctx.status(400).result("intervenantEmail query param est requis");
            return;
        }

        try {
            candidatureService.withdrawCandidature(workRequestId, intervenantEmail);
            ctx.result("Candidature retirée avec succès");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erreur: " + e.getMessage());
        }
    }

    /**
     * Liste les candidatures pour une demande de travaux.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void getCandidatures(Context ctx) {
        Long workRequestId = Long.parseLong(ctx.pathParam("id"));
        try {
            var candidatures = candidatureService.getCandidaturesForWorkRequest(workRequestId);
            ctx.json(candidatures);
        } catch (Exception e) {
            ctx.status(500).result("Server error: " + e.getMessage());
        }
    }

    /**
     * Sélectionne une candidature par le résident.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void selectCandidature(Context ctx) {
        Long workRequestId = Long.parseLong(ctx.pathParam("id"));
        Long candidatureId = Long.parseLong(ctx.pathParam("candidatureId"));

        try {
            Map<String, Object> body = jsonMapper.readValue(ctx.body(), Map.class);
            String residentMessage = body.get("residentMessage") != null ? (String) body.get("residentMessage") : null;

            candidatureService.selectCandidatureByResident(workRequestId, candidatureId, residentMessage);
            ctx.result("Candidature sélectionnée avec succès");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erreur: " + e.getMessage());
        }
    }

    /**
     * Confirme une candidature par l'intervenant.
     *
     * @param ctx Le contexte de la requête HTTP.
     */
    public void confirmCandidature(Context ctx) {
        Long workRequestId = Long.parseLong(ctx.pathParam("id"));
        Long candidatureId = Long.parseLong(ctx.pathParam("candidatureId"));

        try {
            Map<String, Object> body = jsonMapper.readValue(ctx.body(), Map.class);
            String intervenantEmail = (String) body.get("intervenantEmail");
            if (intervenantEmail == null || intervenantEmail.trim().isEmpty()) {
                ctx.status(400).result("intervenantEmail est requis");
                return;
            }

            candidatureService.confirmCandidatureByIntervenant(workRequestId, candidatureId, intervenantEmail);
            ctx.result("Candidature confirmée avec succès");
        } catch (IllegalArgumentException | IllegalStateException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Erreur: " + e.getMessage());
        }
    }
}