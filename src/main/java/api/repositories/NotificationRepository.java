package api.repositories;

import api.DatabaseManager;
import models.Notification;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepository {
    private final DatabaseManager db;

    public NotificationRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Enregistre une nouvelle notification dans la base de données.
     *
     * @param notification La notification à enregistrer.
     * @throws SQLException Si une erreur survient lors de l'enregistrement.
     */
    public void save(Notification notification) throws SQLException {
        String sql = "INSERT INTO notifications (resident_email, message, date_created, is_read) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, notification.getResidentEmail());
            pstmt.setString(2, notification.getMessage());
            pstmt.setString(3, notification.getDateCreated().toString());
            pstmt.setBoolean(4, notification.isRead());
            pstmt.executeUpdate();
        }
    }

    /**
     * Trouve toutes les notifications non lues d'un résident.
     *
     * @param email L'email du résident.
     * @return La liste des notifications non lues.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */
    public List<Notification> findUnreadByResidentEmail(String email) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE resident_email = ? AND is_read = 0";
        List<Notification> notifications = new ArrayList<>();
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                notifications.add(mapResultSet(rs));
            }
        }
        return notifications;
    }

    /**
     * Trouve toutes les notifications d'un résident.
     *
     * @param email L'email du résident.
     * @return La liste de toutes les notifications, triées par date de création décroissante.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */
    public List<Notification> findAllByResidentEmail(String email) throws SQLException {
        String sql = "SELECT * FROM notifications WHERE resident_email = ? ORDER BY date_created DESC";
        List<Notification> notifications = new ArrayList<>();
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                notifications.add(mapResultSet(rs));
            }
        }
        return notifications;
    }

    /**
     * Marque toutes les notifications d'un résident comme lues.
     *
     * @param residentEmail L'email du résident.
     * @throws SQLException Si une erreur survient lors de la mise à jour.
     */
    public void markAsRead(String residentEmail) throws SQLException {
        String sql = "UPDATE notifications SET is_read = 1 WHERE resident_email = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, residentEmail);
            pstmt.executeUpdate();
        }
    }

    /**
     * Mappe un ResultSet à un objet Notification.
     *
     * @param rs Le ResultSet à mapper.
     * @return L'objet Notification mappé.
     * @throws SQLException Si une erreur survient lors du mapping.
     */
    private Notification mapResultSet(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setId(rs.getLong("id"));
        notification.setResidentEmail(rs.getString("resident_email"));
        notification.setMessage(rs.getString("message"));
        notification.setDateCreated(LocalDateTime.parse(rs.getString("date_created")));
        notification.setRead(rs.getBoolean("is_read"));
        return notification;
    }
}
