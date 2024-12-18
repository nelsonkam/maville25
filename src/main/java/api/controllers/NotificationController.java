package api.controllers;

import api.services.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import models.Notification;

import java.util.Map;

public class NotificationController {
    private final NotificationService notificationService;
    private final ObjectMapper jsonMapper;

    public NotificationController(NotificationService notificationService, ObjectMapper jsonMapper) {
        this.notificationService = notificationService;
        this.jsonMapper = jsonMapper;
    }

    public void sendNotification(Context ctx) {
        try {
            Map<String, Object> body = jsonMapper.readValue(ctx.body(), Map.class);
            String residentEmail = (String) body.get("residentEmail");
            String message = (String) body.get("message");

            if (residentEmail == null || message == null) {
                ctx.status(400).result("residentEmail and message are required");
                return;
            }

            notificationService.sendNotification(residentEmail, message);
            ctx.status(201).result("Notification sent successfully");
        } catch (Exception e) {
            ctx.status(500).result("Failed to send notification: " + e.getMessage());
        }
    }

    public void getUnreadNotifications(Context ctx) {
        try {
            String residentEmail = ctx.pathParam("email");
            var notifications = notificationService.getUnreadNotifications(residentEmail);
            ctx.json(notifications);
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch unread notifications: " + e.getMessage());
        }
    }

    public void getAllNotifications(Context ctx) {
        try {
            String residentEmail = ctx.pathParam("email");
            var notifications = notificationService.getAllNotifications(residentEmail);
            ctx.json(notifications);
        } catch (Exception e) {
            ctx.status(500).result("Failed to fetch notifications: " + e.getMessage());
        }
    }

    public void markAsRead(Context ctx) {
        try {
            String residentEmail = ctx.pathParam("email");
            notificationService.markAllAsRead(residentEmail);
            ctx.result("Notifications marked as read");
        } catch (Exception e) {
            ctx.status(500).result("Failed to mark notifications as read: " + e.getMessage());
        }
    }
}
