package api.repositories;

import api.DatabaseManager;
import models.WorkPreference;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dépôt pour gérer les préférences horaires des résidents dans la base de données.
 */
public class WorkPreferenceRepository {
    private final DatabaseManager db;

    /**
     * Constructeur.
     *
     * @param db Instance du gestionnaire de base de données.
     */
    public WorkPreferenceRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Sauvegarde une préférence horaire dans la base de données.
     *
     * @param preference L'objet WorkPreference à sauvegarder.
     * @throws SQLException En cas d'erreur SQL.
     */
    public void save(WorkPreference preference) throws SQLException {
        String sql = "INSERT INTO work_preferences (resident_email, work_request_id, preferred_start_time, preferred_end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, preference.getResidentEmail());
            pstmt.setLong(2, preference.getWorkRequestId());
            pstmt.setString(3, preference.getPreferredStartTime().toString());
            pstmt.setString(4, preference.getPreferredEndTime().toString());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    preference.setId(rs.getLong(1));
                }
            }
        }
    }

    /**
     * Met à jour une préférence horaire existante dans la base de données.
     *
     * @param preference L'objet WorkPreference à mettre à jour.
     * @throws SQLException En cas d'erreur SQL.
     */
    public void update(WorkPreference preference) throws SQLException {
        String sql = "UPDATE work_preferences SET preferred_start_time = ?, preferred_end_time = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, preference.getPreferredStartTime().toString());
            pstmt.setString(2, preference.getPreferredEndTime().toString());
            pstmt.setLong(3, preference.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Récupère toutes les préférences horaires associées à un résident.
     *
     * @param residentEmail L'email du résident.
     * @return Une liste de WorkPreference associées à l'email donné.
     * @throws SQLException En cas d'erreur SQL.
     */
    public List<WorkPreference> findByResidentEmail(String residentEmail) throws SQLException {
        String sql = "SELECT * FROM work_preferences WHERE resident_email = ?";
        List<WorkPreference> preferences = new ArrayList<>();
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, residentEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    preferences.add(mapResultSetToWorkPreference(rs));
                }
            }
        }
        return preferences;
    }

    /**
     * Mappe un ResultSet SQL en un objet WorkPreference.
     *
     * @param rs Le ResultSet.
     * @return Un objet WorkPreference.
     * @throws SQLException En cas d'erreur SQL.
     */
    private WorkPreference mapResultSetToWorkPreference(ResultSet rs) throws SQLException {
        WorkPreference preference = new WorkPreference();
        preference.setId(rs.getLong("id"));
        preference.setResidentEmail(rs.getString("resident_email"));
        preference.setWorkRequestId(rs.getLong("work_request_id"));
        preference.setPreferredStartTime(LocalTime.parse(rs.getString("preferred_start_time")));
        preference.setPreferredEndTime(LocalTime.parse(rs.getString("preferred_end_time")));
        return preference;
    }
}
