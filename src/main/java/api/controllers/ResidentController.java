package api.controllers;

import api.LoginRequest;
import api.response.ResidentResponse;
import api.services.ResidentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Resident;

import java.util.stream.Collectors;

public class ResidentController {
    private final ResidentService residentService;
    private final ObjectMapper jsonMapper;

    public ResidentController(ResidentService residentService, ObjectMapper jsonMapper) {
        this.residentService = residentService;
        this.jsonMapper = jsonMapper;
    }

    public void register(Context ctx) {
        try {
            Resident newResident = jsonMapper.readValue(ctx.body(), Resident.class);
            if (newResident.getEmail() == null || newResident.getEmail().trim().isEmpty() ||
                newResident.getPassword() == null || newResident.getPassword().trim().isEmpty()) {
                ctx.status(400).result("Email and password are required fields");
                return;
            }
            residentService.register(newResident);
            ctx.status(201).json(new ResidentResponse(newResident));
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Server error during registration");
        }
    }

    public void login(Context ctx) {
        try {
            LoginRequest login = jsonMapper.readValue(ctx.body(), LoginRequest.class);
            Resident resident = residentService.login(login.getEmail(), login.getPassword());
            ctx.json(new ResidentResponse(resident));
        } catch (IllegalArgumentException e) {
            ctx.status(401).result("Invalid credentials");
        } catch (Exception e) {
            ctx.status(500).result("Server error during login");
        }
    }

    public void getAllResidents(Context ctx) {
        try {
            var residents = residentService.getAllResidents()
                .stream()
                .map(ResidentResponse::new)
                .collect(Collectors.toList());
            ctx.json(residents);
        } catch (Exception e) {
            ctx.status(500).result("Server error while fetching residents");
        }
    }
}