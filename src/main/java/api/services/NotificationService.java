package api.services;

import api.repositories.NotificationRepository;
import models.Notification;

import java.sql.SQLException;
import java.util.List;

public class NotificationService {
    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    /**
     * Envoie une notification à un résident.
     *
     * @param residentEmail L'email du résident destinataire.
     * @param message Le contenu de la notification.
     * @throws RuntimeException Si l'envoi de la notification échoue.
     */
    public void sendNotification(String residentEmail, String message) {
        try {
            Notification notification = new Notification(residentEmail, message);
            repository.save(notification);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to send notification: " + e.getMessage(), e);
        }
    }

    /**
     * Récupère toutes les notifications non lues d'un résident.
     *
     * @param residentEmail L'email du résident.
     * @return La liste des notifications non lues.
     * @throws RuntimeException Si la récupération des notifications échoue.
     */
    public List<Notification> getUnreadNotifications(String residentEmail) {
        try {
            return repository.findUnreadByResidentEmail(residentEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch unread notifications", e);
        }
    }

    /**
     * Récupère toutes les notifications d'un résident.
     *
     * @param residentEmail L'email du résident.
     * @return La liste de toutes les notifications.
     * @throws RuntimeException Si la récupération des notifications échoue.
     */
    public List<Notification> getAllNotifications(String residentEmail) {
        try {
            return repository.findAllByResidentEmail(residentEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch notifications", e);
        }
    }

    /**
     * Marque toutes les notifications d'un résident comme lues.
     *
     * @param residentEmail L'email du résident.
     * @throws RuntimeException Si la mise à jour des notifications échoue.
     */
    public void markAllAsRead(String residentEmail) {
        try {
            repository.markAsRead(residentEmail);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to mark notifications as read", e);
        }
    }
}
