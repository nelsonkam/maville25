package api.controllers;

import api.services.WorkRequestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.WorkRequest;

import java.time.LocalDate;

public class WorkRequestController {
    private final WorkRequestService workRequestService;
    private final ObjectMapper jsonMapper;

    public WorkRequestController(WorkRequestService workRequestService, ObjectMapper jsonMapper) {
        this.workRequestService = workRequestService;
        this.jsonMapper = jsonMapper;
    }

    public void submitRequest(Context ctx) {
        try {
            WorkRequest request = jsonMapper.readValue(ctx.body(), WorkRequest.class);
            
            // Validate work request
            if (request.getDesiredStartDate().isBefore(LocalDate.now())) {
                ctx.status(400).result("Date must be in the future");
                return;
            }

            // Validate work type
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

    public void getAllRequests(Context ctx) {
        try {
            var requests = workRequestService.getAllWorkRequests();
            ctx.json(requests);
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch work requests: " + e.getMessage());
        }
    }

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
