package api.controllers;

import api.LoginRequest;
import api.response.IntervenantResponse;
import api.services.IntervenantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Intervenant;

import java.util.stream.Collectors;

public class IntervenantController {
    private final IntervenantService intervenantService;
    private final ObjectMapper jsonMapper;

    public IntervenantController(IntervenantService intervenantService, ObjectMapper jsonMapper) {
        this.intervenantService = intervenantService;
        this.jsonMapper = jsonMapper;
    }

    public void register(Context ctx) {
        try {
            Intervenant newIntervenant = jsonMapper.readValue(ctx.body(), Intervenant.class);
            if (newIntervenant.getEmail() == null || newIntervenant.getEmail().trim().isEmpty() ||
                newIntervenant.getPassword() == null || newIntervenant.getPassword().trim().isEmpty()) {
                ctx.status(400).result("Email and password are required fields");
                return;
            }
            intervenantService.register(newIntervenant);
            ctx.status(201).json(new IntervenantResponse(newIntervenant));
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Server error during registration");
        }
    }

    public void login(Context ctx) {
        try {
            LoginRequest login = jsonMapper.readValue(ctx.body(), LoginRequest.class);
            Intervenant intervenant = intervenantService.login(login.getEmail(), login.getPassword());
            ctx.json(new IntervenantResponse(intervenant));
        } catch (IllegalArgumentException e) {
            ctx.status(401).result("Invalid credentials");
        } catch (Exception e) {
            ctx.status(500).result("Server error during login");
        }
    }

    public void getAllIntervenants(Context ctx) {
        try {
            var intervenants = intervenantService.getAllIntervenants()
                .stream()
                .map(IntervenantResponse::new)
                .collect(Collectors.toList());
            ctx.json(intervenants);
        } catch (Exception e) {
            ctx.status(500).result("Server error while fetching intervenants");
        }
    }
}
